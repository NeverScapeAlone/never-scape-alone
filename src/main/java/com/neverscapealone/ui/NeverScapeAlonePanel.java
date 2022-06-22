package com.neverscapealone.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.*;
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
import org.apache.commons.text.WordUtils;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    public static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneClient client;
    private final Client user;

    // SWING OBJECTS
    JButton button_exit = new JButton();
    JButton button_accept = new JButton();
    private final JPanel linksPanel;
    public JPanel serverPanel;
    public final JPanel matchPanel;
    private final Component activityOptionPanelSeperator;
    public final Component matchPanelSeperator;
    public final Component partnerPanelSeperator;
    public final JPanel partnerPanel;
    private final JPanel activityPanelTitle;
    private final JPanel activityOptionPanel;
    private final JPanel skillPanel;
    private final JPanel bossPanel;
    private final JPanel raidPanel;
    private final JPanel minigamePanel;
    private final JPanel miscPanel;
    private final JPanel matchButton;
    private final Component skillPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
    private final Component bossPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
    private final Component raidPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
    private final Component minigamePanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
    private final Component miscPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
    private final JPanel skillPanelTitle = title("Skills");
    private final JPanel bossPanelTitle = title("Bosses");
    private final JPanel raidPanelTitle = title("Raids");
    private final JPanel minigamePanelTitle = title("Minigames");
    private final JPanel miscPanelTitle = title("Miscellaneous");
    private final JPanel acceptOrDeclineButtons;
    private final JButton matchingButton = new JButton();
    private final JButton acceptButton = new JButton();
    private final JButton declineButton = new JButton();
    public ArrayList activity_buttons = new ArrayList<JToggleButton>();
    private ItemEvent itemEventHx = null;
    // SPINNERS
    SpinnerNumberModel party_member_count_numbers = new SpinnerNumberModel(2, 2,100, 1);
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

    // JSON
    public JsonArray player_selections = new JsonArray();
    private int party_member_count_payload = 0;
    private int self_experience_payload = 0;
    private int partner_experience_payload = 0;

    @Getter
    @AllArgsConstructor
    public enum WebLink
    {
        DISCORD(Icons.DISCORD_ICON, "Join our Discord","https://discord.gg/rs2AH3vnmf"),
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        PATREON(Icons.PATREON_ICON, "Support us through Patreon","https://www.patreon.com/bot_detector"),
        PAYPAL(Icons.PAYPAL_ICON, "Support us through PayPal","https://www.paypal.com/paypalme/osrsbotdetector"),
        ETH_ICON(Icons.ETH_ICON, "Support us with Ethereum, you will be sent to our Github", "https://github.com/NeverScapeAlone"),
        BTC_ICON(Icons.BTC_ICON, "Support us with Bitcoin,  you will be sent to our Github","https://github.com/NeverScapeAlone"),
        BUG_REPORT_ICON(Icons.BUG_REPORT, "Submit a bug report here","https://github.com/NeverScapeAlone/never-scape-alone/issues");

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

        // links panel
        linksPanel = linksPanel();

        // server panel
        serverPanel = serverPanel();

        // match panel
        matchPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
        matchPanelSeperator.setVisible(false);
        matchPanel = matchPanel();
        matchPanel.setVisible(false);

        // match button
        matchButton = matchButton();
        acceptOrDeclineButtons = acceptOrDeclineButtons();
        acceptOrDeclineButtons.setVisible(false);

        // partner panel
        partnerPanel = partnerPanel();
        partnerPanel.setVisible(false);
        partnerPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
        partnerPanelSeperator.setVisible(false);

        // activity panel
        activityOptionPanelSeperator = Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT);
        activityPanelTitle = title("Customize Your Activity");
        activityOptionPanel = activityOptionPanelMaker();

        // icon panels
        skillPanel = queuePanel(4,6);
        bossPanel = queuePanel(7,6);
        raidPanel = queuePanel(2,2);
        minigamePanel = queuePanel(6,6);
        miscPanel = queuePanel(1,3);

        // TOOLBAR CONSTRUCTION

        add(linksPanel);

        //server panel
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));
        add(serverPanel);

        // match panel
        add(matchPanelSeperator);
        add(matchPanel);

        // Match Button
        add(matchButton);

        // Partner panel
        add(partnerPanelSeperator);
        add(partnerPanel);

        // Accept or Decline Buttons
        add(acceptOrDeclineButtons);

        // activity panel
        add(activityOptionPanelSeperator);
        add(activityPanelTitle);
        add(activityOptionPanel);

        add(skillPanelSeperator);
        add(skillPanelTitle);
        add(skillPanel);

        add(bossPanelSeperator);
        add(bossPanelTitle);
        add(bossPanel);

        add(raidPanelSeperator);
        add(raidPanelTitle);
        add(raidPanel);

        add(minigamePanelSeperator);
        add(minigamePanelTitle);
        add(minigamePanel);

        add(miscPanelSeperator);
        add(miscPanelTitle);
        add(miscPanel);

        setServerPanel("Plugin Starting", "The plugin is currently starting...", COLOR_INPROGRESS);
        matchButtonManager(QueueButtonStatus.OFFLINE);

        addQueueButtons();
    }

    public JPanel partnerPanel() {
        JPanel partnerPanel = new JPanel();
        partnerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        partnerPanel.setBackground(SUB_BACKGROUND_COLOR);
        partnerPanel.setLayout(new GridBagLayout());
        partnerPanel.add(new JPanel());
        return partnerPanel;
    }

    public void setPartnerPanel(ArrayList<MatchInformation> matchInformationArrayList){
        JPanel usersPanel = (JPanel) partnerPanel.getComponent(0);
        usersPanel.removeAll();

        usersPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        usersPanel.setBackground(SUB_BACKGROUND_COLOR);
        usersPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Border border = BorderFactory.createBevelBorder(1);
        String header = WordUtils.capitalizeFully(matchInformationArrayList.get(0).party_identifier.split("[$]")[0].replace('_',' '));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border,header,TitledBorder.CENTER, TitledBorder.TOP);
        usersPanel.setBorder(titledBorder);

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx=0;
        c.gridy=0;

        for (MatchInformation partner : matchInformationArrayList){
            JPanel userPanel = new JPanel();
            userPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            userPanel.setBackground(BACKGROUND_COLOR);
            userPanel.setLayout(new GridBagLayout());
            GridBagConstraints userC = new GridBagConstraints();
            userC.weightx = 1;
            userC.fill = GridBagConstraints.HORIZONTAL;
            userC.anchor = GridBagConstraints.WEST;

            userC.gridx = 0;
            userC.gridy = 0;
            userPanel.add(new JLabel(partner.login), userC);

            if (partner.has_accepted) {
                userPanel.setBackground(Color.GREEN.darker().darker().darker());
            } else {
                userPanel.setBackground(Color.YELLOW.darker().darker().darker());
            }

            userC.gridx = 0;
            userC.gridy = 1;

            // add UserPanel to UsersPanel

            usersPanel.add(Box.createVerticalStrut(2), c);
            c.gridy+=1;
            usersPanel.add(userPanel, c);
            c.gridy+=1;
        }

        usersPanel.revalidate();
        usersPanel.repaint();

        GridBagConstraints c_panel = new GridBagConstraints();
        c_panel.weightx = 1;
        c_panel.fill = GridBagConstraints.HORIZONTAL;
        c_panel.anchor = GridBagConstraints.WEST;
        partnerPanel.add(usersPanel, c_panel);
    }

    public void setPartnerPanelVisible(Boolean tf){
        partnerPanelSeperator.setVisible(tf);
        partnerPanel.setVisible(tf);
    }

    public void setMatchPanelVisible(Boolean tf){
        matchPanelSeperator.setVisible(tf);
        matchPanel.setVisible(tf);
    }

    public void setButtonPanelVisible(Boolean tf){
        skillPanelSeperator.setVisible(tf);
        skillPanelTitle.setVisible(tf);
        skillPanel.setVisible(tf);

        bossPanelSeperator.setVisible(tf);
        bossPanelTitle.setVisible(tf);
        bossPanel.setVisible(tf);

        raidPanelSeperator.setVisible(tf);
        raidPanelTitle.setVisible(tf);
        raidPanel.setVisible(tf);

        minigamePanelSeperator.setVisible(tf);
        minigamePanelTitle.setVisible(tf);
        minigamePanel.setVisible(tf);

        miscPanelSeperator.setVisible(tf);
        miscPanelTitle.setVisible(tf);
        miscPanel.setVisible(tf);
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

    public void setServerPanel(String display_text, String tool_tip, Color background){
        JLabel label = (JLabel) (serverPanel.getComponent(0));
        serverPanel.setBackground(background);
        label.setText(display_text);
        serverPanel.setToolTipText(tool_tip);
    }

    public JPanel matchPanel(){
        JPanel matchPanel = new JPanel();
        matchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        matchPanel.setBackground(SUB_BACKGROUND_COLOR);

        // border for Match Panel
        Border border = BorderFactory.createBevelBorder(1);
        String header = "Queue Progress";
        TitledBorder titledBorder = BorderFactory.createTitledBorder(border,header,TitledBorder.CENTER, TitledBorder.TOP);
        matchPanel.setBorder(titledBorder);
        matchPanel.setLayout(new GridBagLayout());

        // left panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;

        c.gridx=0;
        c.gridy=0;
        matchPanel.add(new JLabel("Queue Time: 00:00:00"), c);
        return matchPanel;
    }

    public void setMatchPanelQueueTime(String display_text){
        JLabel label = (JLabel) (matchPanel.getComponent(0));
        label.setText(display_text);
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

        button_accept.setEnabled(false);
        button_exit.setEnabled(false);

        button_exit.setIcon(Icons.CANCEL_ICON);
        button_exit.addActionListener(e -> activityPanelCancel(e));
        buttonHolder.add(button_exit);

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
        JLabel party_member_count_title = new JLabel("Party Size");
        party_member_count_title.setFont(FontManager.getRunescapeSmallFont());
        party_member_count_title.setHorizontalAlignment(SwingConstants.CENTER);
        party_member_count.setToolTipText("Your final party size, including yourself.");

        JLabel self_experience_level_title = new JLabel("Your Experience");
        self_experience_level_title.setFont(FontManager.getRunescapeSmallFont());
        self_experience_level_title.setHorizontalAlignment(SwingConstants.CENTER);
        self_experience_level.setToolTipText("Your estimated experience level.");

        JLabel partner_experience_level_title = new JLabel("Party's Experience");
        partner_experience_level_title.setFont(FontManager.getRunescapeSmallFont());
        partner_experience_level_title.setHorizontalAlignment(SwingConstants.CENTER);
        partner_experience_level.setToolTipText("The estimated experience levels of your partners.");

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

    public void setActivityPanelVisible(Boolean tf){
        activityOptionPanelSeperator.setVisible(tf);
        activityPanelTitle.setVisible(tf);
        activityOptionPanel.setVisible(tf);
    }

    private void buttons_ActivityOptions(ItemEvent itemEvent){
        Object object = itemEvent.getItem();
        if (object instanceof JToggleButton){

            // opens selection choices
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
            // disable button status
            button_accept.setEnabled(true);
            button_exit.setEnabled(true);
        }
    }

    private void activityPanelCancel(ActionEvent actionEvent){
        // cancels active item
        Object objectHx = itemEventHx.getItem();
        JToggleButton old_button = ((JToggleButton) objectHx);
        old_button.setSelected(false);

        // disable button status
        button_accept.setEnabled(false);
        button_exit.setEnabled(false);
    }

    private void activityPanelAccept(ActionEvent actionEvent){
        // sets active item
        Object objectHx = itemEventHx.getItem();
        JToggleButton old_button = ((JToggleButton) objectHx);
        old_button.setSelected(true);
        old_button.setEnabled(false);
        old_button.setIcon(Icons.ACCEPT_ICON);

        // load payloads
        String activity_name = old_button.getName();
        appendSelections(activity_name);

        // disable button status
        button_accept.setEnabled(false);
        button_exit.setEnabled(false);

        // check button status
        matchButtonStateSwitcher(player_selections.size());
    }

    private void appendSelections(String activity_name){
        /** player_selections Layout
         *  [
         *   {
         *     "activity": "string",
         *     "configuration": {
         *       "party_member_count": int,
         *       "self_experience_level": int,
         *       "partner_experience_level": int
         *     }
         *   }
         * ]
         */

        // json put goes here
        party_member_count_payload = (Integer) party_member_count.getValue();
        self_experience_payload = ExperienceLevel.valueOf((String) self_experience_level.getValue()).ordinal();
        partner_experience_payload = ExperienceLevel.valueOf((String) partner_experience_level.getValue()).ordinal();

        JsonObject payload = new JsonObject();
        JsonObject sub_payload = new JsonObject();

        sub_payload.addProperty("party_member_count", party_member_count_payload);
        sub_payload.addProperty("self_experience_level", self_experience_payload);
        sub_payload.addProperty("partner_experience_level", partner_experience_payload);
        sub_payload.addProperty("us_east", config.usEast());
        sub_payload.addProperty("us_west", config.usWest());
        sub_payload.addProperty("eu_west", config.euWest());
        sub_payload.addProperty("eu_central", config.euCentral());
        sub_payload.addProperty("oceania", config.oceania());

        boolean f2p = true;
        boolean p2p = true;
        switch(config.worldTypeSelection()){
            case F2P:
                p2p = false;
                break;
            case P2P:
                f2p = false;
                break;
        }

        sub_payload.addProperty("f2p", f2p);
        sub_payload.addProperty("p2p", p2p);

        payload.addProperty("activity", activity_name);
        payload.add("configuration", sub_payload);

        player_selections.add(payload);
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

    private JPanel acceptOrDeclineButtons(){
        JPanel acceptOrDeclinePanel = new JPanel();
        acceptOrDeclinePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        acceptOrDeclinePanel.setBackground(SUB_BACKGROUND_COLOR);
        acceptOrDeclinePanel.setLayout(new GridLayout(1,2));

        acceptButton.setBackground(Color.green.darker().darker().darker());
        acceptButton.setText(QueueButtonStatus.ACCEPT.getName());
        acceptButton.addActionListener(plugin::matchClickManager);

        declineButton.setBackground(Color.red.darker().darker().darker());
        declineButton.setText(QueueButtonStatus.DENY.getName());
        declineButton.addActionListener(plugin::matchClickManager);

        acceptOrDeclinePanel.add(declineButton);
        acceptOrDeclinePanel.add(acceptButton);
        return acceptOrDeclinePanel;
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

    private void matchButtonStateSwitcher(int config_counter){
        // switches match button state
        switch(NeverScapeAlonePlugin.serverStatusState.getStatus()) {
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
                matchButton.setVisible(true);
                acceptOrDeclineButtons.setVisible(false);
                break;
            case ONLINE:
            case END_SESSION:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_INFO);
                matchButton.setVisible(true);
                acceptOrDeclineButtons.setVisible(false);
                break;
            case SELECT_ACTIVITY_MATCH:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_INPROGRESS);
                matchButton.setVisible(true);
                acceptOrDeclineButtons.setVisible(false);
                break;
            case START_QUEUE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_COMPLETED);
                matchButton.setVisible(true);
                acceptOrDeclineButtons.setVisible(false);
                break;
            case CANCEL_QUEUE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_WARNING);
                matchButton.setVisible(true);
                acceptOrDeclineButtons.setVisible(false);
                break;
            case ACCEPT_OR_DECLINE:
                matchingButton.setText(state.getName());
                matchingButton.setBackground(COLOR_INPROGRESS);
                matchButton.setVisible(false);
                acceptOrDeclineButtons.setVisible(true);
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
}
