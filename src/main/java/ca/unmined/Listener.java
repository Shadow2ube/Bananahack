package ca.unmined;

import ca.unmined.commands.Covid;
import ca.unmined.util.Command;
import ca.unmined.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class Listener extends ListenerAdapter implements Component {
    public static HashMap<Long, ArrayList<String>> sent = new HashMap<>();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (Plugin.COMMANDS == null) Util.RegisterAllCommands();
        for (Command c : Plugin.COMMANDS.values()) {
            event.getJDA().upsertCommand(c.name.toLowerCase(), c.description).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        sent.put(event.getAuthor().getIdLong(), new ArrayList<>());
        sent.get(event.getAuthor().getIdLong()).add(event.getMessage().getId());

        if (event.getAuthor().isBot()
                || event.isWebhookMessage()
                || !event.getMessage().getContentRaw().startsWith(Plugin.b_Prefix)) {
            return;
        }
        if (event.getChannelType().equals(ChannelType.TEXT)) {
            Plugin.execute(event);
        }
    }

    MessageEmbed oldmsg = null;

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (!event.getMessage()
                .getEmbeds().get(0).getFooter()
                .getText().split(": ")[1]
                .equals(event.getUser().getId()))
            return;

        if (event.getComponentId().equals("Switch")) {
            if (Covid.graphState == 1) Covid.graphState = 0;
            else Covid.graphState = 1;

            if (oldmsg == null)
                oldmsg = event.getMessage().getEmbeds().get(0);

            event.editMessageEmbeds(messageEdit(event, Covid.graphState)).queue();
            event.editButton(Button.primary("Switch", Covid.mode)).queue();
        }
    }

    public MessageEmbed messageEdit(ButtonClickEvent event, int graphState) {
        switch (graphState) {
            // List
            case 0:
                Covid.mode = "Graph";
                return oldmsg;
            // Graph
            case 1:
                Covid.mode = "List";
                return new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("COVID-19 Cases By Graph:")
                        .setDescription("COVID-19 Rising Per Country By Graph")
                        .clearFields()
                        .setImage(Plugin.allTimeGraph)
                        .setTimestamp(Instant.now())
                        .setFooter("Command Executed By: " + event.getUser().getIdLong())
                        .build();
        }

        return new EmbedBuilder().setColor(Color.RED).setTitle("COVID-19 Cases By Graph:")
                .setDescription("COVID-19 Rising Per Country By Graph")
                .clearFields()
                .setImage(Plugin.allTimeGraph)
                .setTimestamp(Instant.now())
                .setFooter("Command Executed By: " + event.getUser().getIdLong())
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
