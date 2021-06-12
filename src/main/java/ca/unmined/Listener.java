package ca.unmined;

import net.dv8tion.jda.api.entities.ChannelType;
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

        if (event.getAuthor().isBot()
                || event.isWebhookMessage()
                || !event.getMessage().getContentRaw().startsWith(Plugin.b_Prefix)) {
            return;
        }
        if (event.getChannelType().equals(ChannelType.TEXT)) {
            Plugin.execute(event);
        }
    }
}
