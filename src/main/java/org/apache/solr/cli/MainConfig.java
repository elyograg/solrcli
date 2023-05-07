package org.apache.solr.cli;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * A class that holds configuration info and constants.
 *
 * TODO: Decide whether {@link Properties} is the right way to store config
 * data.
 */
public final class MainConfig {
  public static final String SOLR_VERSION = "10.0.0-SNAPSHOT";
  public static final String SYSPROP_STOPKEY = "stopKey";

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final Properties configProperties = new Properties();
  private static final String SEP = FileSystems.getDefault().getSeparator();
  private static final String CONFIG_FILE_NAME = "solr.yml";
  private static final String[] WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS = {
      System.getenv("USERPROFILE") + SEP + "." + CONFIG_FILE_NAME,
      "C:\\Solr" + SEP + CONFIG_FILE_NAME };
  private static final String[] POSIX_DEFAULT_CONFIG_FILE_LOCATIONS = {
      System.getenv("SCRIPT_DIR") + SEP + CONFIG_FILE_NAME,
      System.getenv("HOME") + SEP + "." + CONFIG_FILE_NAME,
      "usr/local/share" + SEP + CONFIG_FILE_NAME, "usr/share/solr" + SEP + CONFIG_FILE_NAME,
      "/etc/default" + SEP + CONFIG_FILE_NAME, "/var/solr" + SEP + CONFIG_FILE_NAME,
      "/opt/solr" + SEP + CONFIG_FILE_NAME };

  private static String configFile;

  /**
   * Parse the cross-platform configuration file, place those settings in this
   * class, and validate the configuration as completely as possible.
   *
   * @return whether or not the validation passed.
   * @throws FileNotFoundException if a readable configuration file was not found.
   */
  public static final boolean validateConfig(final String configFileParam)
      throws FileNotFoundException {
    boolean validated = false;

    if (configFileParam == null || configFileParam.equals("")) {
      configFile = findConfigFile();
    } else {
      configFile = configFileParam;
      if (!Files.isReadable(Paths.get(configFile))) {
        log.error("Specified config file {} does not exist or is not readable.", configFile);
        configFile = null;
      }

      if (configFile == null || configFile.equals("")) {
        throw new FileNotFoundException("Unable to find config file!");
      }

      parseConfig(configFile);

      // TODO: Validate that all required sysProps and/or environment variables are
      // correctly defined. If not, indicate what is missing or incorrect. Display
      // requirements. Delete this meaningless test.
      if (MainCommand.class != null) {
        validated = true;
      }
    }
    return validated;
  }

  /**
   * Parse the provided configuration file and populate the configuration object.
   *
   * @param configFile the full path to the configuration file
   */
  private static void parseConfig(final String configFile) {
    // TODO Auto-generated method stub
  }

  /**
   * Look for a config file in predetermined locations.
   *
   * @return the path to the chosen config file.
   */
  private static String findConfigFile() {
    String configFilePath = null;
    final String[] fileArray;
    if (System.getProperty("os.name").startsWith("Windows")) {
      fileArray = WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS;
    } else {
      fileArray = POSIX_DEFAULT_CONFIG_FILE_LOCATIONS;
    }

    for (final String f : fileArray) {
      if (Files.isReadable(Paths.get(f))) {
        configFilePath = f;
        break;
      }
    }
    // TODO Auto-generated method stub
    return configFilePath;
  }

  public static Properties getConfigProperties() {
    return configProperties;
  }

  public static String getProperty(final String propertyParam) {
    return configProperties.getProperty(propertyParam);
  }
}
