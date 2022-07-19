/*
 * Copyright (c) 2021, Ferrariic, Seltzer Bro, Cyborger1
 * Copyright (c) 2022, Ferrariic
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.neverscapealone.ui;

import com.neverscapealone.NeverScapeAloneConfig;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.ActivityReference;
import com.neverscapealone.http.NeverScapeAloneWebsocket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.game.WorldService;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    public static final Color COLOR_INPROGRESS = ColorScheme.PROGRESS_INPROGRESS_COLOR.darker().darker();

    /// panel match statics
    public static final Color BACKGROUND_COLOR = ColorScheme.DARK_GRAY_COLOR;
    public static final Color SUB_BACKGROUND_COLOR = ColorScheme.DARKER_GRAY_COLOR;
    public static final int SUB_PANEL_SEPARATION_HEIGHT = 7;

    // PANELS
    private final JPanel linksPanel;
    private final JPanel switchMenuPanel;
    private JPanel skillPanel;
    private JPanel bossPanel;
    private JPanel raidPanel;
    private JPanel minigamePanel;
    private JPanel miscPanel;
    private JPanel connectingPanel;

    private JPanel createskillPanel;
    private JPanel createbossPanel;
    private JPanel createraidPanel;
    private JPanel createminigamePanel;
    private JPanel createmiscPanel;
    private final JPanel quickPanel;
    private final JPanel createPanel;
    private final JPanel createPanel2;
    private final JPanel searchPanel;

    // BUTTONS
    private final IconTextField activitySearchBar = new IconTextField();
    private final JButton quickMatchButton = new JButton();
    private final JToggleButton quickMatchPanelButton = new JToggleButton();
    private final JToggleButton createMatchPanelButton = new JToggleButton();
    private final JToggleButton searchMatchPanelButton = new JToggleButton();
    public ArrayList activity_buttons = new ArrayList<JToggleButton>();
    public ArrayList create_activity_buttons = new ArrayList<JToggleButton>();

    // SPINNERS & MODELS
    SpinnerNumberModel min_party_member_count_model = new SpinnerNumberModel(2, 2, 200, 1);
    SpinnerNumberModel max_party_member_count_model = new SpinnerNumberModel(2, 2, 200, 1);

    public final JSpinner min_party_member_count = new JSpinner(min_party_member_count_model);
    public final JSpinner max_party_member_count = new JSpinner(max_party_member_count_model);
    public final JComboBox<String> experience_level = new JComboBox(new String[]{"Flexible", "Novice", "Average", "Experienced"});
    public final JComboBox<String> party_loot = new JComboBox(new String[]{"FFA", "Split"});
    public final JComboBox<String> account_type = new JComboBox(new String[]{"All Accounts", "Normal", "IM", "HCIM", "UIM", "GIM", "HCGIM"});
    public final JComboBox<String> region = new JComboBox(new String[]{"All Regions", "United States", "North Europe", "Central Europe", "Australia"});
    public final JTextField passcode = new JTextField();

    // GLOBAL VARIABLES
    public String step1_activity = "";
    public ArrayList<String> queue_list = new ArrayList<String>();
    public boolean isConnecting = false;

    // CLASSES
    private final NeverScapeAlonePlugin plugin;
    private final NeverScapeAloneConfig config;
    private final NeverScapeAloneWebsocket websocket;
    private final Client user;
    private final WorldService worldService;

    @Getter
    @AllArgsConstructor
    public enum WebLink {
        DISCORD(Icons.DISCORD_ICON, "Join our Discord", "https://discord.gg/rs2AH3vnmf"),
        TWITTER(Icons.TWITTER_ICON, "Follow us on Twitter", "https://www.twitter.com/NeverScapeAlone"),
        GITHUB(Icons.GITHUB_ICON, "Check out the project's source code", "https://github.com/NeverScapeAlone"),
        PATREON(Icons.PATREON_ICON, "Support us through Patreon", "https://www.patreon.com/bot_detector"),
        PAYPAL(Icons.PAYPAL_ICON, "Support us through PayPal", "https://www.paypal.com/paypalme/osrsbotdetector"),
        ETH_ICON(Icons.ETH_ICON, "Support us with Ethereum, you will be sent to our Github", "https://github.com/NeverScapeAlone"),
        BTC_ICON(Icons.BTC_ICON, "Support us with Bitcoin,  you will be sent to our Github", "https://github.com/NeverScapeAlone"),
        BUG_REPORT_ICON(Icons.BUG_REPORT, "Submit a bug report here", "https://github.com/NeverScapeAlone/never-scape-alone/issues");

        private final ImageIcon image;
        private final String tooltip;
        private final String link;

    }


    @Inject
    public NeverScapeAlonePanel(
            NeverScapeAlonePlugin plugin,
            NeverScapeAloneConfig config,
            EventBus eventBus,
            NeverScapeAloneWebsocket websocket,
            Client user,
            WorldService worldService) {
        this.config = config;
        this.plugin = plugin;
        this.websocket = websocket;
        this.user = user;
        this.worldService = worldService;
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(BACKGROUND_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // panel inits
        linksPanel = linksPanel(); // add link panel perm
        switchMenuPanel = switchMenuPanel(); // add switch menu panel perm
        connectingPanel = connectingPanel();
        connectingPanel.setVisible(false);
        // constructions
        constructQueuePanels(); // construct queue panels for placement in quick panel
        constructCreatePanels(); // construct create panels for placement in create panel
        // quick panel
        quickPanel = quickPanel();
        quickPanel.setVisible(true);
        quickMatchPanelButton.setSelected(true);
        // create panel
        createPanel = createPanel();
        createPanel.setVisible(false);
        createMatchPanelButton.setSelected(false);
        // create panel step 2
        createPanel2 = createPanel2();
        createPanel2.setVisible(false);
        // search panel
        searchPanel = searchPanel();
        searchPanel.setVisible(false);
        searchMatchPanelButton.setSelected(false);

        // ADD PANELS
        add(linksPanel);
        add(Box.createVerticalStrut(SUB_PANEL_SEPARATION_HEIGHT));

        add(switchMenuPanel);
        add(Box.createVerticalStrut(5));

        // add quick, create, and search panels -- these should never be visible when the other is visible. So no separator is required, held as a single group.
        add(quickPanel);
        add(createPanel);
        add(createPanel2);
        add(searchPanel);
        add(connectingPanel);

        addQueueButtons();
        addCreateButtons();
    }

    private JPanel linksPanel() {
        JPanel linksPanel = new JPanel();
        linksPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        linksPanel.setBackground(SUB_BACKGROUND_COLOR);
        for (WebLink w : WebLink.values()) {
            JLabel link = new JLabel(w.getImage());
            link.setToolTipText(w.getTooltip());
            link.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    LinkBrowser.browse(w.getLink());
                }
            });
            linksPanel.add(link);
        }
        return linksPanel;
    }

    private JPanel switchMenuPanel() {
        JPanel switchMenuPanel = new JPanel();
        switchMenuPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        switchMenuPanel.setBackground(SUB_BACKGROUND_COLOR);
        switchMenuPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;

        quickMatchPanelButton.setText("Quick");
        quickMatchPanelButton.setToolTipText("Quickly find a match");
        quickMatchPanelButton.addActionListener(e -> quickPanelManager(e));
        switchMenuPanel.add(quickMatchPanelButton, c);
        c.gridx += 1;

        createMatchPanelButton.setText("Create");
        createMatchPanelButton.setToolTipText("Create a new match");
        createMatchPanelButton.addActionListener(e -> createPanelManager(e));
        switchMenuPanel.add(createMatchPanelButton, c);
        c.gridx += 1;

        searchMatchPanelButton.setText("Search");
        searchMatchPanelButton.setToolTipText("Search for active matches");
        searchMatchPanelButton.addActionListener(e -> searchPanelManager(e));
        switchMenuPanel.add(searchMatchPanelButton, c);

        return switchMenuPanel;
    }

    private JPanel connectingPanel(){
        JPanel connectingPanel = new JPanel();
        connectingPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        connectingPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 0;
        c.gridy = 0;
        JButton escape = new JButton();
        escape.setIcon(Icons.CANCEL_ICON);
        escape.setToolTipText("Exit");
        escape.setSize(20,20);
        escape.addActionListener(this::quickPanelManager);
        connectingPanel.add(escape, c);

        c.anchor = GridBagConstraints.CENTER;
        c.gridy += 1;
        connectingPanel.add(title("Connecting..."), c);
        c.gridy += 1;
        connectingPanel.add(new JLabel("Queue Time: 00:00:00"), c);

        return connectingPanel;
    }

    public void setConnectingPanelQueueTime(String display_text){
        JLabel label = (JLabel) (connectingPanel.getComponent(2));
        label.setText(display_text);
    }

    public void connectingPanelManager(ActionEvent actionEvent){
        connectingPanelManager();
    }

    public void connectingPanelManager() {
        switchMenuPanel.setVisible(false);
        isConnecting = true;

        connectingPanel.setVisible(true);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(false);

        quickMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }


    private void quickPanelManager(ActionEvent actionEvent) {
        switchMenuPanel.setVisible(true);
        isConnecting = false;
        plugin.timer = 0;

        connectingPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(true);
        searchPanel.setVisible(false);

        searchMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    private void createPanelManager(ActionEvent actionEvent) {
        switchMenuPanel.setVisible(true);
        isConnecting = false;


        connectingPanel.setVisible(false);
        createPanel.setVisible(true);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(false);

        quickMatchPanelButton.setSelected(false);
        searchMatchPanelButton.setSelected(false);
    }

    private void searchPanelManager(ActionEvent actionEvent) {
        switchMenuPanel.setVisible(true);
        isConnecting = false;

        connectingPanel.setVisible(false);
        createPanel.setVisible(false);
        createPanel2.setVisible(false);
        quickPanel.setVisible(false);
        searchPanel.setVisible(true);

        quickMatchPanelButton.setSelected(false);
        createMatchPanelButton.setSelected(false);
    }

    private JPanel quickPanel() {
        JPanel quickPanel = new JPanel();
        quickPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        quickPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        quickMatchButton.setText("Select Activities");
        quickMatchButton.setToolTipText("Choose activities to play!");
        quickMatchButton.setBackground(COLOR_INPROGRESS);
        quickPanel.add(quickMatchButton, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Skills"), c);
        c.gridy += 1;
        quickPanel.add(skillPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Bosses"), c);
        c.gridy += 1;
        quickPanel.add(bossPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Raids"), c);
        c.gridy += 1;
        quickPanel.add(raidPanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Mini-games"), c);
        c.gridy += 1;
        quickPanel.add(minigamePanel, c);
        c.gridy += 1;

        quickPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        quickPanel.add(title("Miscellaneous"), c);
        c.gridy += 1;
        quickPanel.add(miscPanel, c);
        return quickPanel;
    }

    private JPanel subActivityPanel(int row, int column) {
        if (row == 0 || column == 0) {
            row = 5;
            column = 5;
        }
        JPanel subActivityPanel = new JPanel();
        subActivityPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        subActivityPanel.setBackground(SUB_BACKGROUND_COLOR);
        subActivityPanel.setLayout(new GridLayout(row, column));
        return subActivityPanel;
    }

    private void addQueueButtons() {
        ActivityReference[] values = ActivityReference.values();
        for (ActivityReference value : values) {
            JToggleButton button = new JToggleButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addItemListener(this::activityButtonManager); // add function here
            activity_buttons.add(button);

            switch (value.getActivity()) {
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

    private void activityButtonManager(ItemEvent itemEvent) {
        JToggleButton button = (JToggleButton) itemEvent.getItem();
        String selection = button.getName();
        if (button.isSelected()){
            queue_list.add(selection);
        } else {
            queue_list.remove(selection);
        }

        if (queue_list.size() > 0){
            quickMatchButton.setText("Start Queue");
            quickMatchButton.addActionListener(plugin::quickMatchQueueStart);
            quickMatchButton.setBackground(COLOR_COMPLETED);
        } else {
            quickMatchButton.setText("Select Activities");
            quickMatchButton.removeActionListener(plugin::quickMatchQueueStart);
            quickMatchButton.setBackground(COLOR_INPROGRESS);
        }
    }

    private void constructQueuePanels() {
        skillPanel = subActivityPanel(4, 6);
        bossPanel = subActivityPanel(7, 6);
        raidPanel = subActivityPanel(2, 2);
        minigamePanel = subActivityPanel(6, 6);
        miscPanel = subActivityPanel(1, 3);
    }

    private JPanel createPanel() {
        JPanel createPanel = new JPanel();
        createPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        createPanel.add(instructionTitle("Step 1: Select an Activity"), c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Skills"), c);
        c.gridy += 1;
        createPanel.add(createskillPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Bosses"), c);
        c.gridy += 1;
        createPanel.add(createbossPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Raids"), c);
        c.gridy += 1;
        createPanel.add(createraidPanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Mini-games"), c);
        c.gridy += 1;
        createPanel.add(createminigamePanel, c);
        c.gridy += 1;

        createPanel.add(Box.createVerticalStrut(5), c);
        c.gridy += 1;
        createPanel.add(title("Miscellaneous"), c);
        c.gridy += 1;
        createPanel.add(createmiscPanel, c);

        return createPanel;
    }

    private void addCreateButtons() {
        ActivityReference[] values = ActivityReference.values();
        for (ActivityReference value : values) {
            JButton button = new JButton();
            button.setIcon(value.getIcon());
            button.setPreferredSize(new Dimension(25, 25));
            button.setToolTipText(value.getTooltip());
            button.setName(value.getLabel());
            button.addActionListener(this::create_activityButtonManager); // add function here
            create_activity_buttons.add(button);

            switch (value.getActivity()) {
                case "skill":
                    createskillPanel.add(button);
                    break;
                case "boss":
                    createbossPanel.add(button);
                    break;
                case "minigame":
                    createminigamePanel.add(button);
                    break;
                case "raid":
                    createraidPanel.add(button);
                    break;
                case "misc":
                    createmiscPanel.add(button);
            }
        }
    }

    private void constructCreatePanels() {
        createskillPanel = subActivityPanel(4, 6);
        createbossPanel = subActivityPanel(7, 6);
        createraidPanel = subActivityPanel(2, 2);
        createminigamePanel = subActivityPanel(6, 6);
        createmiscPanel = subActivityPanel(1, 3);
    }

    private void create_activityButtonManager(ActionEvent actionEvent) {
        Object object = actionEvent.getSource();
        if (object instanceof JButton) {
            step1_activity = ((JButton) object).getName();
            createPanel.setVisible(false);
            createPanel2.setVisible(true);
        }
    }

    private JPanel createPanel2() {
        JPanel createPanel2 = new JPanel();
        createPanel2.setBorder(new EmptyBorder(0, 0, 0, 0));
        createPanel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        createPanel2.add(instructionTitle("Step 2: Choose Requirements"), c);

        c.gridy += 1;
        JPanel createSelectionPanel = createSelectionPanel();
        createPanel2.add(createSelectionPanel, c);

        c.gridy += 1;
        createPanel2.add(Box.createVerticalStrut(6), c);

        c.gridy += 1;
        JButton button_confirm = new JButton();
        button_confirm.setBackground(Color.green.darker().darker());
        button_confirm.setText("Create Group");
        button_confirm.setToolTipText("Click here to create a group with your current configuration!");
        button_confirm.setIcon(Icons.NSA_ICON);
        button_confirm.addActionListener(plugin::createMatchStart);
        createPanel2.add(button_confirm, c);

        return createPanel2;
    }



    private JPanel createSelectionPanel() {
        JPanel createSelectionPanel = new JPanel();
        createSelectionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        createSelectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        createSelectionPanel.add(header("Max Members"), c);
        c.gridx = 1;
        createSelectionPanel.add(max_party_member_count, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Min Members"), c);
        c.gridx = 1;
        createSelectionPanel.add(min_party_member_count, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Experience"), c);
        c.gridx = 1;
        ((JLabel)experience_level.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(experience_level, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Split Type"), c);
        c.gridx = 1;
        ((JLabel)party_loot.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(party_loot, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Accounts"), c);
        c.gridx = 1;
        ((JLabel)account_type.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(account_type, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Region"), c);
        c.gridx = 1;
        ((JLabel)region.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        createSelectionPanel.add(region, c);
        c.gridy += 1;

        c.gridx = 0;
        createSelectionPanel.add(header("Passcode"), c);
        c.gridx = 1;
        passcode.setToolTipText("Leave blank for a Public match");
        createSelectionPanel.add(passcode, c);
        c.gridy += 1;

        return createSelectionPanel;
    }

    private JPanel searchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        searchPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        c.gridy = 0;
        c.gridx = 0;

        IconTextField activitySearchBar = activitySearchBar();
        searchPanel.add(activitySearchBar, c);
        c.gridy += 1;

        return searchPanel;
    }

    private IconTextField activitySearchBar() {
        IconTextField searchBar = new IconTextField();
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        searchBar.setBackground(SUB_BACKGROUND_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.setMinimumSize(new Dimension(0, 30));
        searchBar.addActionListener(plugin::searchActiveMatches);
        return searchBar;
    }

    private JPanel title(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label_holder.add(label);
        return label_holder;
    }

    private JPanel header(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(FontManager.getRunescapeSmallFont());
        label_holder.add(label);
        return label_holder;
    }

    private JPanel instructionTitle(String title_text) {
        JPanel label_holder = new JPanel();
        JLabel label = new JLabel(title_text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setForeground(Color.green.darker());
        label_holder.add(label);
        return label_holder;
    }
}