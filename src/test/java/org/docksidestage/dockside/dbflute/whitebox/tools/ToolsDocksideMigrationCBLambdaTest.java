package org.docksidestage.dockside.dbflute.whitebox.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dbflute.helper.filesystem.FileTextIO;
import org.dbflute.helper.filesystem.FileTextLineFilter;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.utflute.core.policestory.PoliceStory;
import org.dbflute.utflute.core.policestory.javaclass.PoliceStoryJavaClassHandler;
import org.dbflute.util.Srl;

/**
 * @author jflute
 */
public class ToolsDocksideMigrationCBLambdaTest extends PlainTestCase {

    public void test_migration_cbLambda() throws Exception {
        policeStoryOfJavaClassChase(new PoliceStoryJavaClassHandler() {
            @Override
            public void handle(File srcFile, Class<?> clazz) {
                if (!isFilteringTarget(srcFile, clazz)) {
                    return;
                }
                final String className = clazz.getName();
                log("...Filtering: " + className);
                final String srcPath = srcFile.getPath();
                final FileTextIO textIO = new FileTextIO().encodeAsUTF8();
                final String text = textIO.read(srcPath);
                textIO.writeFilteringLine(srcPath, text, createLineHandler(text));
            }

            private boolean isFilteringTarget(File srcFile, Class<?> clazz) {
                final String className = clazz.getName();
                if (!className.endsWith("Test")) {
                    return false;
                }
                if (className.contains(".tools.")) {
                    return false;
                }
                return className.contains(".dbflute."); // *point
            }
        });
    }

    protected FileTextLineFilter createLineHandler(String text) {
        boolean useDisp = text.contains("toDisplaySql()");
        return new FileTextLineFilter() {
            private boolean inTest;
            private boolean inCB;
            private String newCBLine;
            private String tableKey;
            private final List<String> cbLineList = new ArrayList<String>();
            private boolean hasBeforeTry;
            private boolean hasAct;

            public String filter(String line) {
                if (inTest) {
                    final String keyOfCBNew = " cb = new ";
                    final String keyOfCBSuffix = "CB()";
                    if (line.contains(keyOfCBNew) && line.contains(keyOfCBSuffix)) {
                        final String closed = closeCBHandling(null);
                        inCB = true;
                        newCBLine = line;
                        tableKey = extractTableKey(line, keyOfCBNew, keyOfCBSuffix);
                        return closed;
                    }
                    if (Srl.containsOrderedAll(line, "Bhv.", "new ", keyOfCBSuffix)) {
                        final String bhvFront = Srl.substringFirstFront(line, "Bhv.");
                        final String bhvRear = Srl.substringFirstRear(line, "Bhv.");
                        final String cbNewFront = Srl.substringFirstFront(bhvRear, "new ");
                        final String cbNewRear = Srl.substringFirstRear(bhvRear, keyOfCBSuffix);
                        return bhvFront + "Bhv." + cbNewFront + "cb -> {}" + cbNewRear;
                    }
                    if (inCB) {
                        return handleCBLambda(line);
                    }
                    final String dispExp = "cb.toDisplaySql()";
                    if (Srl.contains(line, dispExp)) {
                        return replace(line, dispExp, "popCB().toDisplaySql()");
                    }
                }
                if (Srl.startsWith(line, "    public void test_", "    protected ")) {
                    inTest = true;
                }
                final String processed = processFinal(line);
                if (processed != null) {
                    return processed;
                }
                return line;
            }

            private String extractTableKey(String line, final String keyOfCBNew, final String keyOfCBSUffix) {
                return Srl.initUncap(Srl.extractScopeFirst(line, keyOfCBNew, keyOfCBSUffix).getContent());
            }

            private String handleCBLambda(String line) {
                if (line.contains(tableKey + "Bhv.") && Srl.containsAny(line, "(cb", ", cb")) {
                    final String lambdaIndent = "        ";
                    final String cbFront = Srl.substringFirstFront(line, "(cb", ", cb");
                    final String cbRear = Srl.substringFirstRear(line, "(cb", ", cb");
                    final StringBuilder sb = new StringBuilder();
                    if (hasBeforeTry) {
                        sb.append(lambdaIndent).append("try {").append(ln());
                    }
                    final boolean kakkoCB = Srl.contains(line, "(cb");
                    sb.append(cbFront).append(kakkoCB ? "(" : ", ");
                    sb.append("cb -> {").append(ln());
                    if (hasAct) {
                        sb.append(lambdaIndent).append("    /* ## Act ## */").append(ln());
                    }
                    removeFirstEmptyLine(cbLineList);
                    cbLineList.forEach(cbLine -> {
                        if (cbLine.equals("$$try$$")) {
                            return;
                        }
                        sb.append("    ").append(cbLine).append(ln());
                    });
                    if (useDisp) {
                        sb.append(lambdaIndent).append("    pushCB(cb);").append(ln());
                    }
                    sb.append(lambdaIndent).append("}").append(cbRear).append(ln());
                    clearCBInfo();
                    return sb.toString();
                } else {
                    final String processed = processFinal(line);
                    if (processed != null) {
                        return processed;
                    }
                    final String prefixSearchKey = "_PrefixSearch(";
                    final String dateFromToKey = "_DateFromTo(";
                    if (line.contains("## Act ##")) {
                        hasAct = true;
                        if (!cbLineList.isEmpty()) {
                            final int lastIndex = cbLineList.size() - 1;
                            final String lastElement = cbLineList.get(lastIndex);
                            if (lastElement.trim().length() == 0) {
                                cbLineList.remove(lastIndex);
                            }
                        }
                    } else if (line.contains(prefixSearchKey)) {
                        final String likeSearchKey = "_LikeSearch(";
                        final String likeOptionExp = ", op -> op.likePrefix());";
                        final String prefixFront = Srl.substringFirstFront(line, prefixSearchKey);
                        final String prefixRear = Srl.substringFirstRear(line, prefixSearchKey);
                        if (prefixRear.contains(");")) {
                            final String firstArg = Srl.substringLastFront(prefixRear, ");");
                            final String closing = Srl.substringLastRear(prefixRear, ");");
                            cbLineList.add(prefixFront + likeSearchKey + firstArg + likeOptionExp + closing);
                        } else {
                            cbLineList.add(line);
                        }
                    } else if (line.contains(dateFromToKey)) {
                        final String fromToKey = "_FromTo(";
                        final String fromToOptionExp = ", op -> op.compareAsDate());";
                        final String prefixFront = Srl.substringFirstFront(line, dateFromToKey);
                        final String prefixRear = Srl.substringFirstRear(line, dateFromToKey);
                        if (prefixRear.contains(");")) {
                            final String firstArg = Srl.substringLastFront(prefixRear, ");");
                            final String closing = Srl.substringLastRear(prefixRear, ");");
                            cbLineList.add(prefixFront + fromToKey + firstArg + fromToOptionExp + closing);
                        } else {
                            cbLineList.add(line);
                        }
                    } else if (line.contains("    try {")) {
                        hasBeforeTry = true;
                        cbLineList.add("$$try$$");
                    } else {
                        cbLineList.add(line);
                    }
                    return null;
                }
            }

            private String processFinal(String line) {
                if (line.startsWith("    }")) {
                    inTest = false;
                    final String closed = closeCBHandling(line);
                    if (closed != null) {
                        return closed;
                    }
                    clearCBInfo();
                }
                return null;
            }

            private String closeCBHandling(String line) {
                if (cbLineList.isEmpty()) {
                    return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(newCBLine).append(ln());
                removeFirstEmptyLine(cbLineList);
                final String lambdaIndent = "        ";
                cbLineList.forEach(cbLine -> {
                    if (cbLine.equals("$$try$$")) {
                        sb.append(lambdaIndent).append("try {").append(ln());
                    } else {
                        sb.append(cbLine).append(ln());
                    }
                });
                if (line != null) {
                    sb.append(line);
                }
                clearCBInfo();
                return Srl.rtrim(sb.toString());
            }

            private void removeFirstEmptyLine(List<String> cbLineList) {
                if (!cbLineList.isEmpty()) {
                    final String firstElement = cbLineList.get(0);
                    if (firstElement.trim().length() == 0) {
                        cbLineList.remove(0);
                    }
                }
            }

            private void clearCBInfo() {
                inCB = false;
                newCBLine = null;
                cbLineList.clear();
                tableKey = null;
                hasBeforeTry = false;
                hasAct = false;
            }
        };
    }

    @Override
    protected PoliceStory createPoliceStory() {
        return new PoliceStory(this, getProjectDir()) {
            @Override
            protected File getSrcMainJavaDir() {
                return new File(getProjectPath() + "/src/test/java/");
            }
        };
    }

    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
    // wall
}
