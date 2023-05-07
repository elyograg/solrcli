package org.apache.solr.cli;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 *
 * A class that holds configuration info and constants. Everything that is not
 * actually needed by the rest of the program is intentionally set to private,
 * beginning with the configuration file path.
 *
 * TODO: Decide whether {@link Properties} is the right way to store
 * configuration data.
 */
public final class StaticStuff {
  // Internal private stuff.

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  /** The Constant configProperties. */
  private static final Properties configProperties = new Properties();

  /** The Constant SEP. */
  private static final String SEP = FileSystems.getDefault().getSeparator();

  /** The Constant CONFIG_FILE_NAME. */
  private static final String CONFIG_FILE_NAME = "solr.yml";

  private static AtomicBoolean debugFlag = new AtomicBoolean();

  // TODO: Remove "." from these path lists.

  /** The Constant WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS. */
  private static final String[] WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS = {
      System.getenv("SCRIPT_DIR") + SEP + CONFIG_FILE_NAME,
      System.getProperty("user.home") + SEP + "." + CONFIG_FILE_NAME,
      "C:\\Solr" + SEP + CONFIG_FILE_NAME, "." };

  /** The Constant DEFAULT_CONFIG_FILE_LOCATIONS. */
  private static final String[] DEFAULT_CONFIG_FILE_LOCATIONS = {
      System.getenv("SCRIPT_DIR") + SEP + CONFIG_FILE_NAME,
      System.getProperty("user.home") + SEP + "." + CONFIG_FILE_NAME,
      "usr/local/share" + SEP + CONFIG_FILE_NAME, "usr/share/solr" + SEP + CONFIG_FILE_NAME,
      "/etc/default" + SEP + CONFIG_FILE_NAME, "/var/solr" + SEP + CONFIG_FILE_NAME,
      "/opt/solr" + SEP + CONFIG_FILE_NAME, "." };

  /** The config file path. */
  private static Path configFilePath;

  // External constants.

  /** The Constant SOLR_VERSION. */
  public static final String SOLR_VERSION = "10.0.0-SNAPSHOT";

  /** The Constant DEFAULT_SOLR_PORT. */
  public static final int DEFAULT_SOLR_PORT = 8983;

  /** The Constant DEFAULT_SOLR_PORT_STRING. */
  public static final String DEFAULT_SOLR_PORT_STRING = "8983";

  /** The Constant SYSPROP_STOPKEY. */
  public static final String SYSPROP_STOPKEY = "stopKey";

  /** The Constant SYSPROP_STOPKEY. */
  public static final String USAGE_OPTION_SEPARATOR_TEXT = "\nThis help shows an equal sign for separating an option and its value. A space also works, and for single-character options, no separator is required. Documentation will mostly use a space.";

  /** The Constant GC_ZGC_DEFAULT_OPTIONS. */
  public static final String[] GC_ZGC_OPTIONS = { "-XX:+UnlockExperimentalVMOptions", "-XX:+UseZGC",
      "-XX:+ParallelRefProcEnabled", "-XX:+ExplicitGCInvokesConcurrent", "-XX:+AlwaysPreTouch",
      "-XX:+UseNUMA" };

  /** The Constant GC_G1_DEFAULT_OPTIONS. */
  public static final String[] GC_G1_OPTIONS = { "-XX:+UseG1GC", "-XX:+ParallelRefProcEnabled",
      "-XX:MaxGCPauseMillis=100", "-XX:+ExplicitGCInvokesConcurrent", "-XX:+UseStringDeduplication",
      "-XX:+AlwaysPreTouch", "-XX:+UseNUMA" };

  /**
   * Parse the cross-platform configuration file, place those settings in this
   * class, and validate the configuration as completely as possible.
   *
   * @param configFileParam the location of the configuration file. Use null to
   *                        search the default locations.
   * @return whether or not the validation passed.
   * @throws FileNotFoundException if a readable configuration file was not found.
   */
  public static final boolean parseAndValidateConfig(final String configFileParam)
      throws FileNotFoundException {
    // TODO: Start with false when validation is working.
    final boolean validated = true;

    if (configFileParam == null || configFileParam.equals("")) {
      configFilePath = findConfigFile();
    } else {
      configFilePath = Paths.get(configFileParam);
      if (!Files.isReadable(configFilePath)) {
        log.error("Specified config file {} does not exist or is not readable.", configFilePath);
        configFilePath = null;
      }

      if (configFilePath == null) {
        throw new FileNotFoundException("Unable to find config file!");
      }

      parseConfig(configFilePath);

      // TODO: Validate that all required sysProps and/or environment variables are
      // correctly defined. If not, indicate what is missing or incorrect. Display
      // requirements.
    }
    log.info("configFile {{}}", configFilePath);
    return validated;
  }

  /**
   * Parse the provided configuration file and populate the configuration object.
   *
   * @param configFile the full path to the configuration file
   */
  private static final void parseConfig(final Path configFile) {
    // TODO Implement
  }

  /**
   * Look for a configuration file in predetermined locations.
   *
   * @return the path to the chosen configuration file.
   */
  private static final Path findConfigFile() {
    String configFile = null;
    final String[] searchArray;
    if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).startsWith("windows")) {
      searchArray = WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS;
    } else {
      searchArray = DEFAULT_CONFIG_FILE_LOCATIONS;
    }

    for (final String f : searchArray) {
      if (Files.isReadable(Paths.get(f))) {
        configFile = f;
        break;
      }
    }
    return Paths.get(configFile);
  }

  /**
   * Gets a configuration property.
   *
   * @param propertyParam the property param
   * @return the property
   */
  public static final String getProperty(final String propertyParam) {
    return configProperties.getProperty(propertyParam);
  }

  /**
   * Exit program.
   *
   * @param code the code
   */
  public static final void exitProgram(final int... code) {
    int exitCode = 0;
    if (code == null || code.length > 0) {
      exitCode = code[0];
    }
    System.exit(exitCode);
  }

  public static boolean getDebugFlag() {
    return debugFlag.get();
  }

  public static void setDebugFlag(final boolean debug) {
    debugFlag.set(debug);
  }
}
