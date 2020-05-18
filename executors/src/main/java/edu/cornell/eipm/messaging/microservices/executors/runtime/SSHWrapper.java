package edu.cornell.eipm.messaging.microservices.executors.runtime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The SSH wrapper for running command within a docker container.
 *
 * @author Manuele Simi
 */
class SSHWrapper {

    private static final String wrapperTemplate = "executors/ssh_command_wrapper.sh";
    private String content;

    SSHWrapper() throws IOException {
        this.loadTemplate();
    }

    /**
     * Loads the ssh wrapper from the classpath
     *
     * @throws IOException if the template is not found
     */
    private void loadTemplate() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(wrapperTemplate).getFile());
        this.content = new String(Files.readAllBytes(file.toPath()));
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
        this.content = this.content.replaceAll("<HOST_HOSTNAME>", hostname)
                .replaceAll("<HOST_USER>", user)
                .replaceAll("<HOST_USER_ID>", userID);
        command = command.replaceAll("'", "\\'");
        command = command.replaceAll("\"", "\\\"");
        command = command.replaceAll("`", "\\\\\\`");
        return this.content.replaceAll("<SSH_COMMAND>", command);
    }
}
