
# ========================================================================================
#                                                                                 Overview
#                                                                                 ========
The example project for Spring and DBFlute.


# ========================================================================================
#                                                                              Environment
#                                                                              ===========
# ----------------------------------------------------------
#                                         Compile & Database
#                                         ------------------
M2EなどのMaven管理ができる環境が整っていればビルドすることができます。
また、組み込みDB「H2」を利用しているため、そのままテストを実行してExampleを試すことが可能です。

# ----------------------------------------------------------
#                                               DBFluteの構成
#                                               ------------
DBFluteは、大きく三つのもので構成されています。
  o DBFluteモジュール：自動生成のためのDBFlute本体モジュール(mydbflute配下)
  o DBFluteクライアント：プロジェクト固有の情報を管理するクライアント(dbflute_xxx)
  o DBFluteランタイム：実行時のランタイムライブラリ(dbflute-runtime-x-x-x.jar)

DBFluteクライアントがDBFluteモジュールを使ってDBFluteタスクを実行してクラスを自動生成し、
自動生成されたクラスは、DBFluteランタイムを使ってDBアクセスを行います。

# ----------------------------------------------------------
#                                            DBFluteランタイム
#                                            ---------------
DBFluteランタイム(dbflute-runtime-x-x-x.jar)の依存ライブラリは以下の通りです。
  o commons-logging-1.1.1

  ※OGNLはSeasarのmaven2リポジトリ「http://maven.seasar.org/maven2」にあります。

よって、Exampleプロジェクトで使っているその他のライブラリは全てSpring、もしくは、
テスト実行のため(H2やDBCPなど)のライブラリとなります。

Maven2を使った場合のDBFluteランタイムのdependency定義は以下の通りです。

<dependency>
	<groupId>org.seasar.dbflute</groupId>
	<artifactId>dbflute-runtime</artifactId>
	<version>0.9.9.7F</version>
</dependency>

# ----------------------------------------------------------
#                                          DBFlute Beans XML
#                                          -----------------
src/main/resources配下に「dbfluteBeans.xml」があります。
DBFluteのクラス自動生成時に一緒に出力され、これを手動で修正することはありません。

同じ場所に「jdbcBeans.xml」にはDBFluteが使うデータソース(javax.sql.DataSource)が定義されています。
これはExample専用に作られた設定です。Exampleでは「commons-DBCP」を利用しています。


# ========================================================================================
#                                                                              Information
#                                                                              ===========
# ----------------------------------------------------------
#                                              Example Class
#                                              -------------
参考実装のテストケースクラスが以下の通りです：

「src/test/java/com.example.dbflute.spring.dbflute.howto」配下
  o BehaviorBasicTest
  o BehaviorMiddleTest
  o BehaviorPlatinumTest
  o ConditionBeanBasicTest
  o ConditionBeanMiddleTest
  o ConditionBeanPlatinumTest

# ----------------------------------------------------------
#                                                  Test Case
#                                                  ---------
Seasarで利用されている「S2TestCase」のフィールドインジェクションとトランザクション制御の
機能をだけをサポートしてSpringに対応させた「AppSpringTestCase」を利用しています。
よって、テストケースは「S2TestCase」を使っている場合と同じように実装されています。

# ----------------------------------------------------------
#                                                Code Format
#                                                -----------
DBFlute標準のフォーマット定義を利用しています。
Eclipseのデフォルトから主に二つの項目を変更しています。
　o コメントはフォーマットしない
　o マージンを「80」から「120」に
こちらは好みですが、コメントはフォーマットされると困ることが多いため、
OFFにしております。（タグコメントの空白が除去されたりなど）
Eclipseのデフォルトをそのまま使われている方は、dbflute-spring-example直下の
「formatter.xml」をインポートして試してみるのも良いかと思います。


# ========================================================================================
#                                                                            Demo Resource
#                                                                            =============
-- alter table MEMBER alter column MEMBER_ACCOUNT VARCHAR(80) NOT NULL; 
-- alter table MEMBER add column ARGENTINA_READING VARCHAR(30);
