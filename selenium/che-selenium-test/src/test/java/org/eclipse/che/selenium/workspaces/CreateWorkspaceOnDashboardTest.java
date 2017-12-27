/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.selenium.workspaces;

import static org.eclipse.che.selenium.core.constant.TestStacksConstants.JAVA;

import com.google.inject.Inject;
import org.eclipse.che.commons.lang.NameGenerator;
import org.eclipse.che.selenium.core.SeleniumWebDriver;
import org.eclipse.che.selenium.core.client.TestWorkspaceServiceClient;
import org.eclipse.che.selenium.core.constant.TestWorkspaceConstants;
import org.eclipse.che.selenium.core.user.TestUser;
import org.eclipse.che.selenium.pageobject.Loader;
import org.eclipse.che.selenium.pageobject.NotificationsPopupPanel;
import org.eclipse.che.selenium.pageobject.ProjectExplorer;
import org.eclipse.che.selenium.pageobject.dashboard.CreateWorkspace;
import org.eclipse.che.selenium.pageobject.dashboard.Dashboard;
import org.eclipse.che.selenium.pageobject.dashboard.NavigationBar;
import org.eclipse.che.selenium.pageobject.dashboard.workspaces.WorkspaceDetails;
import org.eclipse.che.selenium.pageobject.dashboard.workspaces.Workspaces;
import org.eclipse.che.selenium.pageobject.machineperspective.MachineTerminal;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/** @author Andrey Chizhikov */
public class CreateWorkspaceOnDashboardTest {
  private static final String WORKSPACE = NameGenerator.generate("WsDashboard", 4);

  @Inject private NavigationBar navigationBar;
  @Inject private CreateWorkspace createWorkspace;
  @Inject private TestUser defaultTestUser;
  @Inject private ProjectExplorer projectExplorer;
  @Inject private Loader loader;
  @Inject private MachineTerminal terminal;
  @Inject private Dashboard dashboard;
  @Inject private WorkspaceDetails workspaceDetails;
  @Inject private SeleniumWebDriver seleniumWebDriver;
  @Inject private TestWorkspaceServiceClient workspaceServiceClient;
  @Inject private Workspaces workspaces;
  @Inject private NotificationsPopupPanel notificationsPopupPanel;

  @AfterClass
  public void tearDown() throws Exception {
    workspaceServiceClient.delete(WORKSPACE, defaultTestUser.getName());
  }

  @Test
  public void createWorkspaceOnDashboardTest() {
    dashboard.open();
    dashboard.waitDashboardToolbarTitle();
    dashboard.selectWorkspacesItemOnDashboard();
    dashboard.waitToolbarTitleName("Workspaces");

    workspaces.clickOnNewWorkspaceBtn();

    createWorkspace.waitToolbar();
    createWorkspace.typeWorkspaceName(WORKSPACE);
    createWorkspace.selectStack(JAVA.getId());
    createWorkspace.setMachineRAM("2");
    createWorkspace.clickOnCreateWorkspaceButton();

    seleniumWebDriver.switchFromDashboardIframeToIde();
    notificationsPopupPanel.waitExpectedMessageOnProgressPanelAndClosed(
        TestWorkspaceConstants.RUNNING_WORKSPACE_MESS, 240);
    projectExplorer.waitProjectExplorer();
    loader.waitOnClosed();
    terminal.waitTerminalConsole(20);
  }
}
