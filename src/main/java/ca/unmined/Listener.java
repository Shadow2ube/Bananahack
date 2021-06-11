package ca.unmined;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Listener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getContentRaw().equalsIgnoreCase("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }
    }
}
