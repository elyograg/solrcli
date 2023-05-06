package org.apache.solr.cli;

public final class MainConfig {
  public static final String SOME_CONSTANT_NAME = "some constant value";

  private static String stopKey = "SolrRocks";

  public static final boolean validateConfig() {
    boolean validated = false;
    // TODO: Validate that all required sysProps and/or environment variables are
    // correctly defined. If not, indicate what is missing or incorrect. Display
    // requirements. Delete this meaningless test.
    if (MainCommand.class != null) {
      validated = true;
    }
    return validated;
  }

  public static String getStopKey() {
    // TODO Auto-generated method stub
    return stopKey;
  }
}
