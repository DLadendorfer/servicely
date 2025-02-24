// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.controller;

import com.aero.servicely.ServicelyFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataController {

  ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  private List<Runnable> tasks = new ArrayList<>();

  public DataController(ServicelyFrame servicelyFrame) {
    start();
  }

  public void addTask(Runnable runnable) {
    tasks.add(runnable);
  }

  private void start() {
    scheduler.scheduleAtFixedRate(this::invokeTasks, 0, 3, TimeUnit.SECONDS);
  }

  private void invokeTasks() {
    tasks.stream().parallel().forEach(Runnable::run);
  }
}
