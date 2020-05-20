package edu.cornell.eipm.messaging.microservices.executors.runtime;

import java.io.*;
import java.util.stream.Collectors;

/**
 * The SSH wrapper for running command within a docker container.
 *
 * @author Manuele Simi
 */
class SSHWrapper {

    private static final String wrapperTemplate = "ssh_command_wrapper.sh";
    private static String wrapperContent = "";

    SSHWrapper() throws IOException {
        this.loadTemplate();
    }

    /**
     * Loads the ssh wrapper from the classpath
     *
     * @throws IOException if the template is not found
     */
    private void loadTemplate() throws IOException {
        if (wrapperContent.isEmpty()) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream(wrapperTemplate);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                wrapperContent = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    /**
     * Gets the wrapped command.
     *
     * @param hostname
     * @param user
     * @param userID
     * @param command
     * @return the command wrapped in the script to execute
     */
    String getCommand(String hostname, String user, String userID, String command) {
        String content = wrapperContent;
        content = content.replaceAll("<HOST_HOSTNAME>", hostname)
                .replaceAll("<HOST_USER>", user)
                .replaceAll("<HOST_USER_ID>", userID);
        command = command.replaceAll("'", "\\\\'");
        command = command.replaceAll("\"", "\\\\\"");
        content = content.replace("<SSH_COMMAND>", command);
        return content;
    }
}
