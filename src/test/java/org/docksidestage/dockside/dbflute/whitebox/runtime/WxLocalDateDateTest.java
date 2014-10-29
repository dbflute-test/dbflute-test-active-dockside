package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dbflute.helper.HandyDate;
import org.dbflute.utflute.core.PlainTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/10/26 Sunday)
 */
public class WxLocalDateDateTest extends PlainTestCase {

    public void test_conversion_challenge_epoch() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
        log("LocalDateTime.of(...)              :: " + localDateTime);
        log("toTimestamp(LocalDa.of(...))       :: " + toTimestamp(localDateTime).toLocalDateTime());
        log("Timestamp.valueOf(LocalDa.of(...)) :: " + Timestamp.valueOf(localDateTime));
        log(" - - -");
        String epoch = "1970/01/01 00:00:00";
        log("toTimestamp(...)     :: " + toTimestamp(epoch));
        log("toLocalDateTime(...) :: " + toLocalDateTime(epoch));
        log("toTimestamp(toLocalDateTime(...))  :: " + toTimestamp(toLocalDateTime(epoch)));
        log("toTimestamp(LocalDateTime.of(...)) :: " + toTimestamp(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0)));
        log("Timestamp.v(LocalDateTime.of(...)) :: " + Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0)));
        log("toLocalDateTime(toTimestamp(...))  :: " + toLocalDateTime(toTimestamp(epoch)));
        log("toTimestamp(...).toLocalDateTime() :: " + toTimestamp(epoch).toLocalDateTime());
        log("toLocalDateTime(...).format(...)   :: " + toLocalDateTime(epoch).format(DateTimeFormatter.BASIC_ISO_DATE));
        log("toTimestamp(LocalDateTi.of(...)).l :: " + toTimestamp(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0)).toLocalDateTime());
        log("tol(toTimestamp(LocalDat.of(...))) :: " + toLocalDateTime(toTimestamp(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0))));
    }

    public void test_conversion_challenge_oldDate() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(1582, 10, 4, 0, 0, 0, 0);
        log("LocalDateTime.of(...)              :: " + localDateTime); // 1582-10-04T00:00
        log("toTimestamp(LocalDateTime.of(...)) :: " + toTimestamp(localDateTime)); // 1582-09-23 23:41:01.0
        log("toTimestamp(LocalDate.of(...)).toL :: " + toTimestamp(localDateTime).toLocalDateTime()); // 1582-09-23T23:41:01
        log("Timestamp.valueOf(LocalDa.of(...)) :: " + Timestamp.valueOf(localDateTime)); // 1582-10-04 00:00:00.0
        log("Timestamp.valueOf(Loc.of(...)).toL :: " + Timestamp.valueOf(localDateTime).toLocalDateTime()); // 1582-10-04T00:00
        log(" - - -");
        String oldStr = "1000/01/01 00:00:00";
        LocalDateTime oldLocal = LocalDateTime.of(1000, 1, 1, 0, 0);
        DateTimeFormatter isoDate = DateTimeFormatter.BASIC_ISO_DATE;
        log("toTimestamp(...)     :: " + toTimestamp(oldStr)); // 1000-01-01 00:00:00.0
        log("toLocalDateTime(...) :: " + toLocalDateTime(oldStr)); // 1000-01-06T00:18:59
        log("toTimestamp(toLocalDateTime(...))  :: " + toTimestamp(toLocalDateTime(oldStr))); // 1000-01-01 00:00:00.0
        log("toTimestamp(LocalDateTime.of(...)) :: " + toTimestamp(oldLocal)); // 0999-12-26 23:41:01.0
        log("Timestamp.v(LocalDateTime.of(...)) :: " + Timestamp.valueOf(oldLocal)); // 1000-01-01 00:00:00.0
        log("toLocalDateTime(toTimestamp(...))  :: " + toLocalDateTime(toTimestamp(oldStr))); // 1000-01-06T00:18:59
        log("toTimestamp(...).toLocalDateTime() :: " + toTimestamp(oldStr).toLocalDateTime()); // 1000-01-01T00:00
        log("toLocalDateTime(...).format(...)   :: " + toLocalDateTime(oldStr).format(isoDate)); // 10000106
        log("toTimestamp(LocalDateTi.of(...)).l :: " + toTimestamp(oldLocal).toLocalDateTime()); // 0999-12-26T23:41:01
        log("tol(toTimestamp(LocalDat.of(...))) :: " + toLocalDateTime(toTimestamp(oldLocal))); // 1000-01-01T00:00
    }

    public void test_conversion_challenge_BC() throws Exception {
        log("toTimestamp(0001) :: " + toTimestamp("0001/01/01"));
        log("new HandyDate(0001).getYear() :: " + new HandyDate("0001/01/01").getYear());
        log("toLocalDateTime(toTime(0001)) :: " + toLocalDateTime(toTimestamp("0001/01/01")));
        log("toTimestamp(BC0001)           :: " + toTimestamp("BC0001/01/01"));
        log("toLo...(toTimestamp(BC0001))  :: " + toLocalDateTime(toTimestamp("BC0001/01/01")));
        log("toTimestamp(BC0001).toLo...() :: " + toTimestamp("BC0001/01/01").toLocalDateTime());
        log("new Hand...(BC0001).getYear() :: " + new HandyDate("BC0001/01/01").getYear());
        log("toTimestamp(BC0001).getTime() :: " + toTimestamp("BC0001/01/01").getTime());
        log("new Hand...(BC0001).getTime() :: " + new HandyDate("BC0001/01/01").getDate().getTime());
        log("new (BC0001).addY(1).getTim() :: " + new HandyDate("BC0001/01/01").addYear(1).getDate().getTime());
        log("new (BC0001).addY(1).getTms() :: " + new HandyDate("BC0001/01/01").addYear(1).getTimestamp());
        log("new Handy...(-0001).getYear() :: " + new HandyDate("-0001/01/01").getYear());
    }
}
