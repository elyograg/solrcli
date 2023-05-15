package org.apache.solr.cli;

import java.lang.invoke.MethodHandles;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Help;
import picocli.CommandLine.Help.ColorScheme;
import picocli.CommandLine.IHelpFactory;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;

// TODO: Auto-generated Javadoc
/**
 *
 * A class that holds configuration info and constants. Everything that is not
 * actually needed by the rest of the program is intentionally set to private,
 * beginning with the configuration file path.
 */
public final class StaticStuff {
  // Internal private stuff.

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  /** The Constant configProperties. */
  private static final Properties configProperties = new Properties();

  /** The Constant SEP. */
  private static final String SEP = FileSystems.getDefault().getSeparator();

  /** The configuration filename extension. */
  private static final String CONFIG_FILENAME_EXTENSION = "yml";

  /** The verbose flag. */
  private static AtomicBoolean verboseFlag = new AtomicBoolean(true);

  /** The force flag. */
  private static AtomicBoolean forceFlag = new AtomicBoolean(true);

  /** The configuration file path. */
  private static Path configFilePath;

  /** The service name. */
  private static String SERVICE_NAME = "solr";

  // External constants.

  /** The Constant SOLR_VERSION. */
  public static final String SOLR_VERSION = "10.0.0-SNAPSHOT";

  /** The Constant DEFAULT_SOLR_PORT. */
  public static final int DEFAULT_SOLR_PORT = 8983;

  /** The Constant DEFAULT_SOLR_PORT_STRING. */
  public static final String DEFAULT_SOLR_PORT_STRING = "8983";

  /** The Constant SYSPROP_STOPKEY. */
  public static final String SYSPROP_STOPKEY = "stopKey";

  /**
   * The Constant USAGE_OPTION_SEPARATOR_TEXT.
   */
  public static final String USAGE_OPTION_SEPARATOR_TEXT = "\nThe usage above shows an equal sign for separating an option and its value."
      + " A space also works, and for single-character options, the separator is optional."
      + " Documentation will mostly use a space."
      + " Use quotes to wrap values that contain spaces or other special characters.";

  /** The Constant GC_ZGC_OPTIONS. */
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
   */
  public static final boolean parseAndValidateConfig(final String configFileParam) {
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
        throw new RuntimeException("Unable to find config file!");
      }

      parseConfig(configFilePath);

      /*
       * TODO: Validate, as much as possible, that the config file, sysProps, and/or
       * environment variables are correctly defined. Report all problems that can be
       * determined. Change validated accordingly.
       */
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
   * Look for a configuration file in predetermined locations. TODO: Make the
   * predetermined locations configurable rather than being semi-hard-coded here.
   *
   * @return the path to the chosen configuration file.
   */
  private static final Path findConfigFile() {
    final String CONFIG_FILE_NAME = String.format("%s.%s", SERVICE_NAME, CONFIG_FILENAME_EXTENSION);
    String configFile = null;
    final List<String> WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS = Collections
        .synchronizedList(new ArrayList<>());
    final List<String> DEFAULT_CONFIG_FILE_LOCATIONS = Collections
        .synchronizedList(new ArrayList<>());
    final String[] COMMON_CONFIG_FILE_LOCATIONS_ARRAY = {
        String.format("%s%s%s", System.getProperty("script.dir"), SEP, CONFIG_FILE_NAME),
        String.format("%s%s.%s", System.getProperty("user.home"), SEP, CONFIG_FILE_NAME) };

    final String[] WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS_ARRAY = {
        String.format("%s%s%s", "C:\\Solr", SEP, CONFIG_FILE_NAME) };
    final String[] DEFAULT_CONFIG_FILE_LOCATIONS_ARRAY = {
        String.format("%s%s%s", "/etc/default", SEP, CONFIG_FILE_NAME),
        String.format("%s%s%s", "/usr/local/share", SEP, CONFIG_FILE_NAME),
        String.format("%s%s%s", "/usr/share/solr", SEP, CONFIG_FILE_NAME),
        String.format("%s%s%s", "/var/solr", SEP, CONFIG_FILE_NAME),
        String.format("%s%s%s", "/opt/solr", SEP, CONFIG_FILE_NAME) };

    DEFAULT_CONFIG_FILE_LOCATIONS.addAll(Arrays.asList(COMMON_CONFIG_FILE_LOCATIONS_ARRAY));
    DEFAULT_CONFIG_FILE_LOCATIONS.addAll(Arrays.asList(DEFAULT_CONFIG_FILE_LOCATIONS_ARRAY));
    WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS.addAll(Arrays.asList(COMMON_CONFIG_FILE_LOCATIONS_ARRAY));
    WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS
        .addAll(Arrays.asList(WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS_ARRAY));
    final List<String> searchList;
    if (System.getProperty("os.name").toLowerCase(Locale.getDefault()).startsWith("windows")) {
      searchList = WINDOWS_DEFAULT_CONFIG_FILE_LOCATIONS;
    } else {
      searchList = DEFAULT_CONFIG_FILE_LOCATIONS;
    }

    for (final String f : searchList) {
      logDebug(log, "Checking config location {}", f);
      if (Files.isReadable(Paths.get(f))) {
        configFile = f;
        break;
      }
    }

    if (configFile == null) {
      throw new RuntimeException("Unable to find config file!");
    }

    return Paths.get(configFile);
  }

  /**
   * Gets a configuration property.
   *
   * @param propertyParam the property parameter
   * @return the property
   */
  public static final String getProperty(final String propertyParam) {
    return configProperties.getProperty(propertyParam);
  }

  /**
   * Exit program.
   *
   * @param code the exit code to use
   */
  public static final void exitProgram(final int... code) {
    int exitCode = 0;
    if (code == null || code.length > 0) {
      exitCode = code[0];
    }
    System.exit(exitCode);
  }

  public static boolean getVerboseFlag() {
    return verboseFlag.get();
  }

  public static void setVerboseFlag(final boolean debugParam) {
    verboseFlag.set(debugParam);
    logDebug(log, "Setting debug {}", debugParam);
  }

  public static boolean getForceFlag() {
    return forceFlag.get();
  }

  public static void setForceFlag(final boolean forceParam) {
    forceFlag.set(forceParam);
    logDebug(log, "Setting force {}", forceParam);
  }

  public static String getServiceName() {
    return SERVICE_NAME;
  }

  public static void setServiceName(final String nameParam) {
    SERVICE_NAME = nameParam;
    logDebug(log, "Setting service name {}", nameParam);
  }

  public static void sleep(final long duration, final TimeUnit unit) {
    long millis = 0L;
    int nanos = 0;

    switch (unit) {
    case SECONDS:
      nanos = 0;
      millis = duration * 1000;
      break;
    case MILLISECONDS:
      nanos = 0;
      millis = duration;
      break;
    case MINUTES:
      nanos = 0;
      millis = duration * 60000;
      break;
    case MICROSECONDS:
      millis = 0;
      nanos = (int) (duration * 1000);
    case NANOSECONDS:
      millis = 0;
      nanos = (int) duration;
    default:
      throw new RuntimeException(
          String.format("Unit %s not valid for this implementation.", unit.toString()));
    }
    try {
      Thread.sleep(millis, nanos);
    } catch (final InterruptedException e) {
      log.warn("Sleep of {} {} interrupted!", duration, unit.toString(), e);
    }
  }

  /**
   * This method was obtained from the picocli project issue tracker. It causes
   * options in the automatically generated help/usage text to all be
   * left-aligned. Without this, it indents some options further than other
   * options, which looks weird.
   *
   * @return {@link IHelpFactory} object for usage formatting.
   */
  public static final IHelpFactory createLeftAlignedUsageHelp() {
    return new IHelpFactory() {
      private static final int COLUMN_REQUIRED_OPTION_MARKER_WIDTH = 2;
      private static final int COLUMN_SHORT_OPTION_NAME_WIDTH = 2;
      private static final int COLUMN_OPTION_NAME_SEPARATOR_WIDTH = 2;
      private static final int COLUMN_LONG_OPTION_NAME_WIDTH = 22;

      private static final int INDEX_REQUIRED_OPTION_MARKER = 0;
      private static final int INDEX_SHORT_OPTION_NAME = 1;
      private static final int INDEX_OPTION_NAME_SEPARATOR = 2;
      private static final int INDEX_LONG_OPTION_NAME = 3;
      private static final int INDEX_OPTION_DESCRIPTION = 4;

      @Override
      public Help create(final CommandSpec commandSpec, final ColorScheme colorScheme) {
        return new Help(commandSpec, colorScheme) {
          @Override
          public Layout createDefaultLayout() {

            // The default layout creates a TextTable with 5 columns, as follows:
            // 0: empty text or (if configured) the requiredOptionMarker character
            // 1: short option name
            // 2: comma separator (if option has both short and long option)
            // 3: long option name(s)
            // 4: option description
            //
            // The code below creates a TextTable with 3 columns, as follows:
            // 0: empty text or (if configured) the requiredOptionMarker character
            // 1: all option names, comma-separated if necessary
            // 2: option description

            final int optionNamesColumnWidth = COLUMN_SHORT_OPTION_NAME_WIDTH
                + COLUMN_OPTION_NAME_SEPARATOR_WIDTH + COLUMN_LONG_OPTION_NAME_WIDTH;

            final TextTable table = TextTable.forColumnWidths(colorScheme,
                COLUMN_REQUIRED_OPTION_MARKER_WIDTH, optionNamesColumnWidth,
                commandSpec.usageMessage().width()
                    - (optionNamesColumnWidth + COLUMN_REQUIRED_OPTION_MARKER_WIDTH));
            final Layout result = new Layout(colorScheme, table, createDefaultOptionRenderer(),
                createDefaultParameterRenderer()) {
              @Override
              public void layout(final ArgSpec argSpec, final Ansi.Text[][] cellValues) {

                // The default option renderer produces 5 Text values for each option.
                // Below we combine the short option name, comma separator and long option name
                // into a single Text object, and we pass 3 Text values to the TextTable.
                for (final Ansi.Text[] original : cellValues) {
                  if (original[INDEX_OPTION_NAME_SEPARATOR].getCJKAdjustedLength() > 0) {
                    original[INDEX_OPTION_NAME_SEPARATOR] = original[INDEX_OPTION_NAME_SEPARATOR]
                        .concat(" ");
                  }
                  final Ansi.Text[] threeColumns = new Ansi.Text[] {
                      original[INDEX_REQUIRED_OPTION_MARKER],
                      original[INDEX_SHORT_OPTION_NAME]
                          .concat(original[INDEX_OPTION_NAME_SEPARATOR])
                          .concat(original[INDEX_LONG_OPTION_NAME]),
                      original[INDEX_OPTION_DESCRIPTION], };
                  table.addRowValues(threeColumns);
                }
              }
            };
            return result;
          }
        };
      }
    };
  }

  public static void logDebug(final Logger loggerParam, final String format,
      final Object... arguments) {
    if (verboseFlag.get()) {
      loggerParam.debug(format, arguments);
    }
  }
}
