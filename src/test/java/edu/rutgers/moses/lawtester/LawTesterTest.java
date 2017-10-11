package edu.rutgers.moses.lawtester;

import org.junit.Test;

import java.util.List;

public class LawTesterTest {
  private static String DUMMY_LAW_PATH = "/dummy.law";

  private static String TRIVIAL_LAW_PATH = "/trivial.law";
  private static String TRIVIAL_LAWTEST_PATH = "/trivial.lawtest";

  private static String PINGPONG_LAW_PATH = "/pingpong.law";
  private static String PINGPONG_LAWTEST_PATH = "/pingpong.lawtest";

  private static String MONITOR_LAW_PATH = "/monitor.law";
  private static String MONITOR_LAWTEST_PATH = "/monitor.lawtest";

  @Test
  public void testTrivialLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(TRIVIAL_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(TRIVIAL_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 1);
    assert(resultList.get(0).getIsSuccess());
  }

  @Test
  public void testPingpongLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(PINGPONG_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(PINGPONG_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 6);
    for (LawTestResult result : resultList) {
      assert(result.getIsSuccess());
    }
  }

  @Test
  public void testMonitorLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(MONITOR_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(MONITOR_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 3);
    for (LawTestResult result : resultList) {
      assert(result.getIsSuccess());
    }
  }

  @Test
  public void testBadTrivialLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(DUMMY_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(TRIVIAL_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 1);
    assert(!resultList.get(0).getIsSuccess());
  }

  @Test
  public void testBadPingpongLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(DUMMY_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(PINGPONG_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 6);
    assert(!resultList.get(2).getIsSuccess());
    assert(!resultList.get(4).getIsSuccess());
  }

  @Test
  public void testBadMonitorLaw() throws Exception {
    LawTester lawTester = new LawTester()
        .setLawStream(this.getClass().getResourceAsStream(DUMMY_LAW_PATH))
        .setLawTestStream(this.getClass().getResourceAsStream(MONITOR_LAWTEST_PATH));
    List<LawTestResult> resultList = lawTester.run();
    assert(resultList.size() == 3);
    for (LawTestResult result : resultList) {
      assert(!result.getIsSuccess());
    }
  }
}