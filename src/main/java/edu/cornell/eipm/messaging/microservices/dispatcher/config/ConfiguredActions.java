package edu.cornell.eipm.messaging.microservices.dispatcher.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Manuele Simi
 */
public class ConfiguredActions {

    private List<Action> actions;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Set<String> getTopicNames() {
        return actions.stream().map(Action::getTopic).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "ConfiguredActions{" +
                "actions=" + actions +
                '}';
    }

    public List<Message> getMessages(String topic) {
        Action action = actions.stream()
                .filter(t -> topic.equals(t.getTopic()))
                .findAny()
                .orElse(null);
        return action.getMessages();
    }
}
