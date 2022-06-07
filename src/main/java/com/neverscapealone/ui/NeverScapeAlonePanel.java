package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.ActivityReference;
import com.neverscapealone.enums.ExperienceLevel;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.enums.ServerStatusCode;
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
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NeverScapeAlonePanel extends PluginPanel {

    @Inject
    ConfigManager configManager;

    // COLOR SELECTIONS

    public static final Color SERVER_SIDE_ERROR = ColorScheme.PROGRESS_ERROR_COLOR.darker().darker().darker();
    public static final Color CLIENT_SIDE_ERROR = Color.MAGENTA.darker().darker().darker();
    public static final Color COLOR_DISABLED = ColorScheme.DARKER_GRAY_COLOR;
    public static final Color COLOR_INFO = ColorScheme.GRAND_EXCHANGE_LIMIT.darker().darker().darker();
    public static final Color COLOR_WARNING = ColorScheme.GRAND_EXCHANGE_ALCH.darker().darker().darker();
    public static final Color COLOR_COMPLETED = ColorScheme.PROGRESS_COMPLETE_COLOR.darker().darker().darker();
    public static final Color COLOR_INPROGRESS = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker().darker();

    /// panel match statics
    public static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    public static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    public static final Color LINK_HEADER_COLOR = ColorScheme.LIGHT_GRAY_COLOR;
    public static final Font NORMAL_FONT = FontManager.getRunescapeFont();
    public static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneClient client;
    private final Client user;

    // SWING OBJECTS
    private final JPanel linksPanel;
    public JPanel serverPanel;
    private final JPanel matchPanel;
    private final Component activityOptionPanelSeperator;
    private final JPanel activityPanelTitle;
    private final JPanel activityOptionPanel;
    private final JPanel skillPanel;
    private final JPanel bossPanel;
    private final JPanel raidPanel;
    private final JPanel soloPanel;
    private final JPanel minigamePanel;
    private final JPanel miscPanel;

    private final JPanel matchButton;
    private final JButton matchingButton = new JButton();

    // VARS

    public ArrayList activity_buttons = new ArrayList<JToggleButton>();
    private ItemEvent itemEventHx = null;

    private final JLabel username_label = new JLabel();
    private final JLabel queue_progress_label = new JLabel();
    private final JLabel queue_time_label = new JLabel();
    private final JLabel world_types_label = new JLabel();
    private final JLabel partner_usernames_label = new JLabel();
    private final JLabel activity_label = new JLabel();
    private final JLabel world_number_label = new JLabel();
    private final JLabel location_label = new JLabel();

    // SPINNERS

    SpinnerNumberModel party_member_count_numbers = new SpinnerNumberModel(1, 1,100, 1);
    SpinnerListModel self_experience_level_list = new SpinnerListModel(new String[] { "Learner","Novice","Apprentice","Adept","Expert","Master" });
    SpinnerListModel partner_experience_level_list = new SpinnerListModel(new String[] { "Learner","Novice","Apprentice","Adept","Expert","Master" });
    private final JSpinner party_member_count = new JSpinner(party_member_count_numbers);
    private final JSpinner self_experience_level = new JSpinner(self_experience_level_list);
    private final JSpinner partner_experience_level = new JSpinner(partner_experience_level_list);

    // ENUMS
    private ActivityReference activityReference;
    private QueueButtonStatus queueButtonStatus;
    private ExperienceLevel experienceLevel;

    private ServerStatusCode serverStatusCode;

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

        // activity panel
        activityOptionPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
        activityOptionPanelSeperator.setVisible(false);

        activityPanelTitle = title("Customize Your Activity");
        activityPanelTitle.setVisible(false);

        activityOptionPanel = activityOptionPanelMaker();
        activityOptionPanel.setVisible(false);

        // icon panels
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
        add(activityOptionPanelSeperator);
        add(activityPanelTitle);
        add(activityOptionPanel);
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

        checkServerStatus("");
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

    public void checkServerStatus(String login) {
        // if no login, for some reason, shut everything down.
        if ((login.isEmpty())) {
            setServerPanel("LOGIN TO RUNESCAPE", "To start the plugin, please login to Old School RuneScape.", COLOR_INFO);
            matchButtonManager(QueueButtonStatus.OFFLINE);
            return;
        }

        // if server is being checked, state in status bar and put queue button manager offline + deactivate buttons
        setServerPanel("CHECKING SERVER", "Checking server for connectivity...", COLOR_INPROGRESS);
        matchButtonManager(QueueButtonStatus.OFFLINE);

        String token = config.authToken();
        client.requestServerStatus(login, token).whenCompleteAsync((status, ex) ->
                SwingUtilities.invokeLater(() ->
                {
                    // in the case of a server error - shut down plugin's systems.
                    if (status == null || ex != null) {
                        plugin.serverStatusState = status;
                        setServerPanel("SERVER ERROR", "There was a server error. Please contact support.", SERVER_SIDE_ERROR);
                        matchButtonManager(QueueButtonStatus.OFFLINE);
                        return;
                    }

                    switch (status.getStatus()) {
                        // if server is alive, start normally, load button state,
                        case ALIVE:
                            plugin.serverStatusState = status;
                            setServerPanel("SERVER ONLINE", "Server is Online. Authentication was successful.", COLOR_COMPLETED);
                            matchButtonManager(QueueButtonStatus.ONLINE);
                            buttons_LoadState(activity_buttons);
                            break;
                        // if server is under maintenance shut down plugin panel
                        case MAINTENANCE:
                            plugin.serverStatusState = status;
                            setServerPanel("SERVER MAINTENANCE", "Server is undergoing Maintenance. Authentication was successful.", COLOR_WARNING);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        // if server is unreachable shut down plugin panel
                        case UNREACHABLE:
                            plugin.serverStatusState = status;
                            setServerPanel("SERVER UNREACHABLE", "Server is Unreachable. No connection could be made.", COLOR_DISABLED);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        // if there is an auth failure, shut down panel (proceed with authing the user)
                        case AUTH_FAILURE:
                            plugin.serverStatusState = status;
                            setServerPanel("AUTH FAILURE", "Authentication failed. Please set a new token in the Plugin config.", CLIENT_SIDE_ERROR);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        // badly formatted token
                        case BAD_TOKEN:
                            plugin.serverStatusState = status;
                            setServerPanel("BAD TOKEN", "The token (auth token) you have entered in the config is malformed.<br> Please delete this token entirely, and turn the plugin on and off.<br>If you need further assistance, please contact Plugin Support.", CLIENT_SIDE_ERROR);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        case BAD_HEADER:
                            plugin.serverStatusState = status;
                            setServerPanel("BAD HEADER", "The incoming header value is incorrect. Please contact Plugin Support.", CLIENT_SIDE_ERROR);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        case BAD_RSN:
                            plugin.serverStatusState = status;
                            setServerPanel("BAD RSN", "The incoming RSN does not match Jagex Standards. please contact Plugin Support.", CLIENT_SIDE_ERROR);
                            matchButtonManager(QueueButtonStatus.OFFLINE);
                            break;
                        // if user is unregistered, mark as being registered and complete registration steps.
                        case REGISTERING:
                            plugin.serverStatusState = status;
                            setServerPanel("REGISTERING ACCOUNT", "Your account is being registered for the plugin.<br>If this process does not complete quickly, please visit Plugin Support.", COLOR_INFO);
                            client.registerUser(login, token).whenCompleteAsync((status_2, ex_2) ->
                                    SwingUtilities.invokeLater(() ->
                                    {
                                        {
                                            if (status_2 == null || ex_2 != null) {
                                                plugin.serverStatusState = status_2;
                                                setServerPanel("SERVER REGISTRATION ERROR", "There was a server registration error. Please contact support.", SERVER_SIDE_ERROR);
                                                matchButtonManager(QueueButtonStatus.OFFLINE);
                                                return;
                                            }
                                            switch (status_2.getStatus()) {
                                                case REGISTRATION_FAILURE:
                                                    plugin.serverStatusState = status_2;
                                                    setServerPanel("REGISTRATION ERROR", "There was a registration error. Please contact support.", SERVER_SIDE_ERROR);
                                                    matchButtonManager(QueueButtonStatus.OFFLINE);
                                                    break;
                                                case REGISTERED:
                                                    plugin.serverStatusState = status_2;
                                                    setServerPanel("SUCCESSFULLY REGISTERED", "You were successfully registered for the plugin. Welcome to NeverScapeAlone!", COLOR_COMPLETED);
                                                    matchButtonManager(QueueButtonStatus.ONLINE);
                                                    buttons_LoadState(activity_buttons);
                                                    break;
                                            }
                                        }
                                    }));
                    }
                }));
    }

    private JPanel serverPanel()
    {
        JPanel serverPanel = new JPanel();
        serverPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        serverPanel.add(new JLabel());
        return serverPanel;
    }

    public void setServerPanel(String display_text, String tool_tip, Color background){
        JLabel label = (JLabel) (serverPanel.getComponent(0));
        serverPanel.setBackground(background);
        label.setText(display_text);
        serverPanel.setToolTipText(tool_tip);
        return;
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

        for (int i=0; i<=9; i++){
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

    private JPanel activityOptionPanelMaker(){
        //init activity bar
        JPanel activityOptionPanelMaker = new JPanel();

        activityOptionPanelMaker.setBorder(new EmptyBorder(0, 0, 0, 0));
        activityOptionPanelMaker.setBackground(SUB_BACKGROUND_COLOR);
        activityOptionPanelMaker.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // init buttons
        JPanel buttonHolder = new JPanel();
        buttonHolder.setBorder(new EmptyBorder(0, 0, 0, 0));
        buttonHolder.setBackground(SUB_BACKGROUND_COLOR);
        buttonHolder.setLayout(new GridLayout(1,2));

        JButton button_exit = new JButton();
        button_exit.setIcon(Icons.CANCEL_ICON);
        button_exit.addActionListener(e -> activityPanelCancel(e));
        buttonHolder.add(button_exit);

        JButton button_accept = new JButton();
        button_accept.setIcon(Icons.ACCEPT_ICON);
        button_accept.addActionListener(e -> activityPanelAccept(e));
        buttonHolder.add(button_accept);

        // init sliders
        JPanel spinnerHolder = new JPanel();
        spinnerHolder.setBorder(new EmptyBorder(0, 0, 0, 0));
        spinnerHolder.setBackground(SUB_BACKGROUND_COLOR);
        spinnerHolder.setLayout(new GridBagLayout());
        GridBagConstraints c_spinner = new GridBagConstraints();

        // add sliders
        JLabel party_member_count_title = new JLabel("Party Members");
        party_member_count_title.setFont(FontManager.getRunescapeSmallFont());
        party_member_count_title.setHorizontalAlignment(SwingConstants.CENTER);
        party_member_count.setToolTipText("The number of other players you would like to have in your party.");

        JLabel self_experience_level_title = new JLabel("Your Experience");
        self_experience_level_title.setFont(FontManager.getRunescapeSmallFont());
        self_experience_level_title.setHorizontalAlignment(SwingConstants.CENTER);
        self_experience_level.setToolTipText("Your estimated experience level.");

        JLabel partner_experience_level_title = new JLabel("Party's Experience");
        partner_experience_level_title.setFont(FontManager.getRunescapeSmallFont());
        partner_experience_level_title.setHorizontalAlignment(SwingConstants.CENTER);
        partner_experience_level.setToolTipText("The minimum experience levels of your partners.");

        // construct spinner subsection
        c_spinner.fill = GridBagConstraints.HORIZONTAL;
        c_spinner.anchor = GridBagConstraints.CENTER;
        c_spinner.weightx = 1;

        c_spinner.gridx=0;
        c_spinner.gridy=0;
        spinnerHolder.add(party_member_count_title, c_spinner);

        c_spinner.gridx=1;
        c_spinner.gridy=0;
        spinnerHolder.add(party_member_count, c_spinner);

        c_spinner.gridx=0;
        c_spinner.gridy=1;
        spinnerHolder.add(self_experience_level_title, c_spinner);

        c_spinner.gridx=1;
        c_spinner.gridy=1;
        spinnerHolder.add(self_experience_level,c_spinner);

        c_spinner.gridx=0;
        c_spinner.gridy=2;
        spinnerHolder.add(partner_experience_level_title, c_spinner);

        c_spinner.gridx=1;
        c_spinner.gridy=2;
        spinnerHolder.add(partner_experience_level, c_spinner);

        // Add sections to activity panel option maker
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = .05;

        c.gridx=0;
        c.gridy=0;
        activityOptionPanelMaker.add(spinnerHolder, c);

        c.gridx=0;
        c.gridy=1;
        activityOptionPanelMaker.add(buttonHolder, c);

        return activityOptionPanelMaker;
    }

    private void buttons_ActivityOptions(ItemEvent itemEvent){
        Object object = itemEvent.getItem();
        if (object instanceof JToggleButton){


            if (itemEventHx != null){
                if (itemEventHx != itemEvent){
                    Object objectHx = itemEventHx.getItem();
                    JToggleButton old_button = ((JToggleButton) objectHx);

                    if (old_button.isSelected() && old_button.isEnabled()){
                        old_button.setSelected(false);
                    }

                }
            }

            itemEventHx = itemEvent;
            activityOptionPanel.setVisible(true);
            activityOptionPanelSeperator.setVisible(true);
            activityPanelTitle.setVisible(true);
        }
    }

    private void activityPanelCancel(ActionEvent actionEvent){
        Object objectHx = itemEventHx.getItem();
        JToggleButton old_button = ((JToggleButton) objectHx);

        old_button.setSelected(false);

        activityOptionPanel.setVisible(false);
        activityOptionPanelSeperator.setVisible(false);
        activityPanelTitle.setVisible(false);
    }

    private void activityPanelAccept(ActionEvent actionEvent){
        Object objectHx = itemEventHx.getItem();
        JToggleButton old_button = ((JToggleButton) objectHx);
        old_button.setSelected(true);
        old_button.setEnabled(false);

        // add image change when activity is selected
        old_button.setIcon(Icons.ACCEPT_ICON);

        activityOptionPanel.setVisible(false);
        activityOptionPanelSeperator.setVisible(false);
        activityPanelTitle.setVisible(false);
    }

    private JPanel matchButton(){
        JPanel matchButton = new JPanel();
        matchButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchButton.setBackground(SUB_BACKGROUND_COLOR);
        matchButton.setLayout(new GridLayout(1,1));

        matchingButton.setText("Offline");
        matchingButton.setBackground(COLOR_DISABLED);
        matchingButton.addActionListener(plugin::matchClickManager);
        matchButton.add(matchingButton);
        return matchButton;
    }

    private void addQueueButtons(){
        ActivityReference[] values = ActivityReference.values();
        for(ActivityReference value: values) {
            // button construction
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addItemListener(e -> buttons_ActivityOptions(e));
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

    public void buttons_LoadState(ArrayList<JToggleButton> activity_buttons){
        configManager.setConfiguration(NeverScapeAloneConfig.CONFIG_GROUP, NeverScapeAloneConfig.CONFIG_TRUE,0);
        int config_counter = 0;

        for (JToggleButton button : activity_buttons){
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
        ButtonStateSwitcher(config_counter);
    }


    private void buttons_StoreState(ItemEvent itemEvent){
        int config_counter = 0;
        Object object = itemEvent.getItem();

        if (object instanceof JToggleButton){
            String label = "config_"+((JToggleButton) object).getName().toLowerCase();
            if(((JToggleButton) object).isSelected()){
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

        ButtonStateSwitcher(config_counter);
    }

    private void ButtonStateSwitcher(int config_counter){
        switch(plugin.serverStatusState.getStatus()) {
            case ALIVE:
            case REGISTERED:
            case QUEUE_CANCELED:
                if ((config_counter == 0)) {
                    matchButtonManager(QueueButtonStatus.SELECT_ACTIVITY_MATCH);
                } else if (config_counter > 0) {
                    matchButtonManager(QueueButtonStatus.START_QUEUE);
                }
                break;
            case BAD_RSN:
            case BAD_TOKEN:
            case BAD_HEADER:
            case MAINTENANCE:
            case REGISTERING:
            case UNREACHABLE:
            case AUTH_FAILURE:
            case REGISTRATION_FAILURE:
            case QUEUE_STARTED_FAILURE:
            case QUEUE_CANCELED_FAILURE:
                matchButtonManager(QueueButtonStatus.OFFLINE);
                break;
            case QUEUE_STARTED:
                matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                break;
        }
    }

    public void matchButtonManager(QueueButtonStatus state){
        switch (state) {
            case OFFLINE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_DISABLED);
                break;
            case ONLINE:
            case END_SESSION:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_INFO);
                break;
            case SELECT_ACTIVITY_MATCH:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_INPROGRESS);
                break;
            case ACCEPT:
            case START_QUEUE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_COMPLETED);
                break;
            case CANCEL_QUEUE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_WARNING);
                break;
        }
    }
    private JPanel title(String title_text){
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
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

    public void setUsername_label(String username){
        username_label.setText(username);
    }

    public void setWorld_types_label(String world_types){
        world_types_label.setText(world_types);
    }
}
