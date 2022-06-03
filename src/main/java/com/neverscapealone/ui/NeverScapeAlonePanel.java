package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.ActivityReference;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.http.NeverScapeAloneClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class NeverScapeAlonePanel extends PluginPanel {

    @Inject
    ConfigManager configManager;

    // COLOR SELECTIONS
    private static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    private static final Color SERVER_UNREACHABLE = ColorScheme.DARKER_GRAY_COLOR;
    private static final Color AUTH_FAILURE = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    private static final Color SERVER_ERROR = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    private static final Color LOGIN_REQUESTED = ColorScheme.GRAND_EXCHANGE_LIMIT.darker().darker();
    private static final Color SERVER_MAINTENANCE = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker();
    private static final Color CHECKING_SERVER = ColorScheme.GRAND_EXCHANGE_LIMIT;
    private static final Color SERVER_ONLINE = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();

    /// button match colors

    private static final Color START_QUEUE = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();
    private static final Color CANCEL_QUEUE = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();

    private static final Color ACCEPT_QUEUE = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();
    private static final Color DENY_QUEUE = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();

    private static final Color SELECT_ACTIVITY_MATCH = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker().darker();
    private static final Color END_SESSION = ColorScheme.GRAND_EXCHANGE_LIMIT.darker().darker().darker();

    /// panel match statics
    private static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    private static final Color LINK_HEADER_COLOR = ColorScheme.LIGHT_GRAY_COLOR;
    private static final Font NORMAL_FONT = FontManager.getRunescapeFont();
    private static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneClient client;
    private final Client user;

    // SWING OBJECTS
    private final JPanel linksPanel;
    private JPanel serverPanel;
    private JPanel matchPanel;
    private JPanel skillPanel;
    private JPanel bossPanel;
    private JPanel raidPanel;
    private JPanel soloPanel;
    private JPanel minigamePanel;
    private JPanel miscPanel;

    private JPanel matchButton;
    private JButton matchingButton = new JButton();

    // VARS

    private ArrayList activity_buttons = new ArrayList<JToggleButton>();

    private JLabel username_label = new JLabel();
    private JLabel queue_progress_label = new JLabel();
    private JLabel queue_time_label = new JLabel();
    private JLabel world_types_label = new JLabel();
    private JLabel partner_usernames_label = new JLabel();
    private JLabel activity_label = new JLabel();
    private JLabel world_number_label = new JLabel();
    private JLabel location_label = new JLabel();

    // ENUMS
    private ActivityReference activityReference;
    private QueueButtonStatus queueButtonStatus;

    @Getter
    @AllArgsConstructor
    public enum WebLink
    {
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter!", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        PATREON(Icons.PATREON_ICON, "Support us here!","https://www.patreon.com/bot_detector");

        private final ImageIcon image;
        private final String tooltip;
        private final String link;
    }

    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            EventBus eventBus, NeverScapeAloneClient client, Client user)
    {
        this.config = config;
        this.plugin = plugin;
        this.client = client;
        this.user = user;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        linksPanel = linksPanel();
        serverPanel = serverPanel();

        matchPanel = matchPanel();
        matchButton = matchButton();

        skillPanel = queuePanel(4,6);
        bossPanel = queuePanel(4,6);
        soloPanel = queuePanel(3,6);
        raidPanel = queuePanel(2,2);
        minigamePanel = queuePanel(6,6);
        miscPanel = queuePanel(1,3);

        add(linksPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(serverPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(matchPanel);
        add(matchButton);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Skills"));
        add(skillPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Multi-Bosses"));
        add(bossPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Solo-Bosses"));
        add(soloPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Raids"));
        add(raidPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Minigames"));
        add(minigamePanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(title("Miscellaneous"));
        add(miscPanel);

        // panel checks
        checkServer("");
        addQueueButtons();
    }


    private JPanel linksPanel()
    {
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        linksPanel.setBackground(SUB_BACKGROUND_COLOR);
        for (WebLink w : WebLink.values())
        {
            JLabel link = new JLabel(w.getImage());
            link.setToolTipText(w.getTooltip());
            link.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    LinkBrowser.browse(w.getLink());
                }
            });
            linksPanel.add(link);
        }
        return linksPanel;
    }

    private JPanel serverPanel()
    {
        JPanel serverPanel = new JPanel();
        serverPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        serverPanel.add(new JLabel());
        return serverPanel;
    }

    public JPanel matchPanel(){
        JPanel matchPanel = new JPanel();
        matchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchPanel.setBackground(SUB_BACKGROUND_COLOR);

        // border for Match Panel
        Border border = BorderFactory.createBevelBorder(1);
        String header = "NeverScapeAlone";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border,header,TitledBorder.CENTER, TitledBorder.TOP);
        matchPanel.setBorder(titledBorder);
        matchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // left panel
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = .05;

        c.gridx=0;
        c.gridy=0;
        matchPanel.add(headerText("Your Information"), c);

        c.gridy=1;
        matchPanel.add(subheaderText("username"), c);

        c.gridy=2;
        matchPanel.add(subheaderText("queue"), c);

        c.gridy=3;
        matchPanel.add(subheaderText("queue time"), c);

        c.gridy=4;
        matchPanel.add(subheaderText("world types"), c);

        c.gridy=5;
        matchPanel.add(headerText("Partner Information"), c);

        c.gridy=6;
        matchPanel.add(subheaderText("partners"), c);

        c.gridy=7;
        matchPanel.add(subheaderText("activity"), c);

        c.gridy=8;
        matchPanel.add(subheaderText("world number"), c);

        c.gridy=9;
        matchPanel.add(subheaderText("location"), c);

        // middle panel

        c.anchor = GridBagConstraints.CENTER;
        c.weightx = .25;
        c.gridx=1;

        int i = 0;
        for (i=0; i<=9; i++){
            c.gridy=i;
            matchPanel.add(subheaderText("|"), c);
        }

        // right panel

        c.anchor = GridBagConstraints.EAST;
        c.weightx = 0;
        c.gridx=2;
        c.gridy=0;
        matchPanel.add(subheaderText(" "), c);

        c.gridy=1;
        username_label.setText("---");
        username_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(username_label, c);

        c.gridy=2;
        queue_progress_label.setText("---");
        queue_progress_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(queue_progress_label, c);

        c.gridy=3;
        queue_time_label.setText("---");
        queue_time_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(queue_time_label, c);

        c.gridy=4;
        world_types_label.setText("---");
        world_types_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(world_types_label, c);

        c.gridy=5;
        matchPanel.add(subheaderText(" "), c);

        c.gridy=6;
        partner_usernames_label.setText("---");
        partner_usernames_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(partner_usernames_label, c);

        c.gridy=7;
        activity_label.setText("---");
        activity_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(activity_label, c);

        c.gridy=8;
        world_number_label.setText("---");
        world_number_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(world_number_label, c);

        c.gridy=9;
        location_label.setText("---");
        location_label.setFont(FontManager.getRunescapeSmallFont());
        matchPanel.add(location_label, c);

        return matchPanel;
    }

    public void setUsername_label(String username){
        username_label.setText(username);
        return;
    }

    public void setWorld_types_label(String world_types){
        world_types_label.setText(world_types);
        return;
    }

    private JPanel matchButton(){
        JPanel matchButton = new JPanel();
        matchButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchButton.setBackground(SUB_BACKGROUND_COLOR);
        matchButton.setLayout(new GridLayout(1,1));

        // placeholder button for matchButtonManager to deal with
        matchingButton.setText("Offline");
        matchingButton.setBackground(SERVER_UNREACHABLE);
        matchButton.add(matchingButton);

        return matchButton;
    }

    public void matchButtonManager(QueueButtonStatus state){
        switch (state) {
            case OFFLINE:
                matchingButton.setText("Offline");
                matchingButton.setBackground(SERVER_UNREACHABLE);
                break;
            case SELECT_ACTIVITY_MATCH:
                matchingButton.setText("Select an Item");
                matchingButton.setBackground(SELECT_ACTIVITY_MATCH);
                break;
            case REQUEST:
                matchingButton.setText("Accept Queue");
                matchingButton.setBackground(ACCEPT_QUEUE);
                break;
            case START_QUEUE:
                matchingButton.setText("Start Queue");
                matchingButton.setBackground(START_QUEUE);
                break;
            case CANCEL_QUEUE:
                matchingButton.setText("Cancel Queue");
                matchingButton.setBackground(CANCEL_QUEUE);
                break;
            case END_SESSION:
                matchingButton.setText("End Session");
                matchingButton.setBackground(END_SESSION);
                break;
        }
    }

    private JLabel headerText(String text){
        JLabel header = new JLabel();
        header.setText("<HTML><U>"+text+"</U></HTML>");
        header.setFont(FontManager.getRunescapeFont());
        return header;
    }
    private JLabel subheaderText(String text){
        JLabel subheader = new JLabel();
        subheader.setText(text);
        subheader.setFont(FontManager.getRunescapeSmallFont());
        return subheader;
    }

    private JPanel queuePanel(int row, int column){
        if (row==0 || column==0){
            row=5;
            column=5;
        }
        JPanel queuePanel = new JPanel();
        queuePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        queuePanel.setBackground(SUB_BACKGROUND_COLOR);
        queuePanel.setLayout(new GridLayout(row, column));
        return queuePanel;
    }

    private JPanel title(String title_text){
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
    }

    private void addQueueButtons(){
        ActivityReference values[] = activityReference.values();
        for(ActivityReference value: values) {
            // button construction
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addItemListener(e -> buttons_StoreState(e));
            button.setEnabled(false);
            activity_buttons.add(button);

            switch(value.getActivity()){
                case "skill":
                    skillPanel.add(button);
                    break;
                case "solo":
                    soloPanel.add(button);
                    break;
                case "boss":
                    bossPanel.add(button);
                    break;
                case "minigame":
                    minigamePanel.add(button);
                    break;
                case "raid":
                    raidPanel.add(button);
                    break;
                case "misc":
                    miscPanel.add(button);
            }
        }
    }

    private void buttons_LoadState(ArrayList<JToggleButton> activity_buttons){
        configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE,0);
        int config_counter = 0;

        for (JToggleButton button : activity_buttons){
            button.setEnabled(true);
            String label_lower = "config_"+button.getName().toLowerCase();
            String state = configManager.getConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, label_lower);
            if (Objects.equals(state, "true")){
                button.setSelected(true);
                config_counter++;
            } else {
                button.setSelected(false);
            }
        }
        configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE, config_counter);

        if (config_counter == 0){
            matchButtonManager(QueueButtonStatus.SELECT_ACTIVITY_MATCH);
        } else if(config_counter > 0) {
            matchButtonManager(QueueButtonStatus.START_QUEUE);
        }
    }

    private void buttons_StoreState(ItemEvent itemEvent){
        int config_counter = 0;
        boolean previous_config = (config.countConfigTrue() >= 1);
        Object object = itemEvent.getItem();

        if (object instanceof JToggleButton){
            String label = "config_"+((JToggleButton) object).getName().toLowerCase();
            if(((JToggleButton) object).isSelected()==true){
                configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, label, true);
                config_counter = Integer.parseInt(configManager.getConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE));
                config_counter++;
                configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE,config_counter);
            } else {
                configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, label, false);
                config_counter = Integer.parseInt(configManager.getConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE));
                config_counter--;
                configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE,config_counter);
            }
        }

        if ((config_counter == 0)){
            // show select activity match for accounts that don't have a previous config but do have zero config counter.
            matchButtonManager(QueueButtonStatus.SELECT_ACTIVITY_MATCH);
        } else if(config_counter > 0) {
            // allow players to queue if they have
            matchButtonManager(QueueButtonStatus.START_QUEUE);
        }
    }

    private void buttons_Deactivate(ArrayList<JToggleButton> activity_buttons){
        for (JToggleButton button : activity_buttons) {
            button.setEnabled(false);
        }
    }

    public void checkServer(String login) {
        JLabel label = (JLabel) (serverPanel.getComponent(0));
        String token = config.authToken();

        if ((login.isEmpty())) {
            serverPanel.setBackground(LOGIN_REQUESTED);
            label.setText("LOGIN TO RUNESCAPE");
            serverPanel.setToolTipText("To start the plugin, please login to Old School RuneScape.");
            matchButtonManager(queueButtonStatus.OFFLINE);
            buttons_Deactivate(activity_buttons);
            return;
        }

        System.out.println(login);

        serverPanel.setBackground(CHECKING_SERVER);
        serverPanel.setToolTipText("Checking server for connectivity...");
        label.setText("CHECKING SERVER");
        matchButtonManager(queueButtonStatus.OFFLINE);
        buttons_Deactivate(activity_buttons);

        client.requestServerStatus(login, token).whenCompleteAsync((status, ex) ->
                SwingUtilities.invokeLater(() ->
                {
                    if (status == null || ex != null) {
                        serverPanel.setBackground(SERVER_ERROR);
                        label.setText("SERVER ERROR");
                        serverPanel.setToolTipText("There was a server error. Please contact support.");
                        matchButtonManager(queueButtonStatus.OFFLINE);
                        buttons_Deactivate(activity_buttons);
                        return;
                    }

                    switch (status.getStatus()) {
                        case ALIVE:
                            serverPanel.setBackground(SERVER_ONLINE);
                            label.setText("SERVER ONLINE");
                            serverPanel.setToolTipText("Server is Online. Authentication was successful.");
                            System.out.println("3");
                            matchButtonManager(queueButtonStatus.SELECT_ACTIVITY_MATCH);
                            buttons_LoadState(activity_buttons);
                            break;
                        case MAINTENANCE:
                            serverPanel.setBackground(SERVER_MAINTENANCE);
                            label.setText("SERVER MAINTENANCE");
                            serverPanel.setToolTipText("Server is undergoing Maintenance. Authentication was successful.");
                            matchButtonManager(queueButtonStatus.OFFLINE);
                            buttons_Deactivate(activity_buttons);
                            break;
                        case UNREACHABLE:
                            serverPanel.setBackground(SERVER_UNREACHABLE);
                            label.setText("SERVER UNREACHABLE");
                            serverPanel.setToolTipText("Server is Unreachable. No connection could be made.");
                            matchButtonManager(queueButtonStatus.OFFLINE);
                            buttons_Deactivate(activity_buttons);
                            break;
                        case AUTH_FAILURE:
                            serverPanel.setBackground(AUTH_FAILURE);
                            label.setText("AUTH FAILURE");
                            serverPanel.setToolTipText("Authentication failed. Please set a new token in the Plugin config.");
                            matchButtonManager(queueButtonStatus.OFFLINE);
                            buttons_Deactivate(activity_buttons);
                    }
                }));
    }
}
