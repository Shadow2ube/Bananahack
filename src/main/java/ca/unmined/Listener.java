package ca.unmined;

import ca.unmined.commands.covid.Covid;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

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

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("GraphHighCase")) {
            event.editMessageEmbeds(messageEdit(event)).complete();

        }
    }

    public MessageEmbed messageEdit(ButtonClickEvent event, int graphState) {
        switch (graphState) {
            // List
            case 0:
                Covid.mode = "Graph";
                break;
            // Graph
            case 1:
                Covid.mode = "List";
                return new EmbedBuilder().setColor(Color.RED).setTitle("COVID-19 Cases By Graph:")
                        .setDescription("COVID-19 Rising Per Country By Graph").clearFields().setImage("https://moz.com/cms/_1200x630_crop_center-center_82_none/GuideToLinkBuilding-OG-Title.png?mtime=20210326151847&focal=none&tmtime=20210610115603")
                        .build();
        }

        return new EmbedBuilder().setColor(Color.RED).setTitle("COVID-19 Cases By Graph:")
                .setDescription("COVID-19 Rising Per Country By Graph").clearFields().setImage("https://moz.com/cms/_1200x630_crop_center-center_82_none/GuideToLinkBuilding-OG-Title.png?mtime=20210326151847&focal=none&tmtime=20210610115603")
                .build();
    }

    @NotNull
    @Override
    public Type getType() {
        return null;
    }

    @Nullable
    @Override
    public String getId() {
        return null;
    }

    @NotNull
    @Override
    public DataObject toData() {
        return null;
    }
}
