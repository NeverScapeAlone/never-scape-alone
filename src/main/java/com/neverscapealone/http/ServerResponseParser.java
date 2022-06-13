package com.neverscapealone.http;

import com.google.inject.Inject;
import com.neverscapealone.NeverScapeAlonePlugin;
import com.neverscapealone.enums.QueueButtonStatus;
import com.neverscapealone.model.ServerStatus;

import javax.swing.*;

public class ServerResponseParser extends NeverScapeAlonePlugin {

    @Inject
    public void parser(ServerStatus status, String login, String token) {
        switch (status.getStatus()) {
            // if server is alive
            case ALIVE:
                serverStatusState = status;
                panel.setServerPanel("SERVER ONLINE", "Server is Online. Authentication was successful.", panel.COLOR_COMPLETED);
                panel.matchButtonManager(QueueButtonStatus.ONLINE);
                break;
            // if server is under maintenance shut down plugin panel
            case MAINTENANCE:
                serverStatusState = status;
                panel.setServerPanel("SERVER MAINTENANCE", "Server is undergoing Maintenance. Authentication was successful.", panel.COLOR_WARNING);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // if server is unreachable shut down plugin panel
            case UNREACHABLE:
                serverStatusState = status;
                panel.setServerPanel("SERVER UNREACHABLE", "Server is Unreachable. No connection could be made.", panel.COLOR_DISABLED);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // if there is an auth failure, shut down panel (proceed with authing the user)
            case AUTH_FAILURE:
                serverStatusState = status;
                panel.setServerPanel("AUTH FAILURE", "Authentication failed. Please set a new token in the Plugin config.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // badly formatted token
            case BAD_TOKEN:
                serverStatusState = status;
                panel.setServerPanel("BAD TOKEN", "The token (auth token) you have entered in the config is malformed.<br> Please delete this token entirely, and turn the plugin on and off.<br>If you need further assistance, please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // bad header
            case BAD_HEADER:
                serverStatusState = status;
                panel.setServerPanel("BAD HEADER", "The incoming header value is incorrect. Please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // bad rsn
            case BAD_RSN:
                serverStatusState = status;
                panel.setServerPanel("BAD RSN", "The incoming RSN does not match Jagex Standards. please contact Plugin Support.", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // if queue was started
            case QUEUE_STARTED:
                serverStatusState = status;
                panel.setServerPanel("IN QUEUE", "You are currently in queue. Please standby for a partner!", panel.CLIENT_SIDE_ERROR);
                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                panel.matchPanelSeperator.setVisible(true);
                panel.matchPanel.setVisible(true);
                break;
            // if queue was canceled
            case QUEUE_CANCELED:
                serverStatusState = status;
                panel.setServerPanel("QUEUE CANCELED","Your queue has been canceled.",panel.COLOR_INFO);
                panel.matchButtonManager(QueueButtonStatus.START_QUEUE);
                panel.matchPanelSeperator.setVisible(false);
                panel.matchPanel.setVisible(false);
                break;
            // if the active matches were scanned, and there are currently no active matches
            case NO_ACTIVE_MATCHES:
                serverStatusState = status;
                panel.setServerPanel("Looking for Partners","There are currently no active matches, please standby while we find you a match.", panel.COLOR_INFO);
                panel.matchButtonManager(QueueButtonStatus.CANCEL_QUEUE);
                panel.matchPanelSeperator.setVisible(true);
                panel.matchPanel.setVisible(true);
                break;
                // if the match was accepted
            case MATCH_ACCEPTED:
                serverStatusState = status;
                panel.setServerPanel("Match Accepted","You have accepted the match. We are now sending your match information to you.",panel.COLOR_COMPLETED);
                panel.matchButtonManager(QueueButtonStatus.END_SESSION);
                panel.matchPanelSeperator.setVisible(true);
                panel.matchPanel.setVisible(true);
                break;
            case REGISTERING:
                serverStatusState = status;
                panel.setServerPanel("REGISTERING ACCOUNT", "Your account is being registered for the plugin.<br>If this process does not complete quickly, please visit Plugin Support.", panel.COLOR_INFO);
                super.clientConnection.registerUser(login, token).whenCompleteAsync((status_2, ex_2) ->
                        SwingUtilities.invokeLater(() ->
                        {
                            {
                                if (status_2 == null || ex_2 != null) {
                                    serverStatusState = status_2;
                                    panel.setServerPanel("SERVER REGISTRATION ERROR", "There was a server registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
                                    panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                                    panel.matchPanelSeperator.setVisible(false);
                                    panel.matchPanel.setVisible(false);
                                    return;
                                }
                                switch (status_2.getStatus()) {
                                    case REGISTRATION_FAILURE:
                                        serverStatusState = status_2;
                                        panel.setServerPanel("REGISTRATION ERROR", "There was a registration error. Please contact support.", panel.SERVER_SIDE_ERROR);
                                        panel.matchButtonManager(QueueButtonStatus.OFFLINE);
                                        panel.matchPanelSeperator.setVisible(false);
                                        panel.matchPanel.setVisible(false);
                                        break;
                                    case REGISTERED:
                                        serverStatusState = status_2;
                                        panel.setServerPanel("SUCCESSFULLY REGISTERED", "You were successfully registered for the plugin. Welcome to NeverScapeAlone!", panel.COLOR_COMPLETED);
                                        panel.matchButtonManager(QueueButtonStatus.ONLINE);
                                        break;
                                }
                            }
                        }));
        }
    }
}
