/**
 * Copyright (C) 2014-2015 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.perf;

import java.io.FileInputStream;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;


/**
 * Simple code to run queries against a server
 * USAGE: QueryRunner confFile query numberOfTimesToRun
 */
public class QueryRunner {
  public static void main(String[] args) throws Exception {
    String confFile = args[0];

    PerfBenchmarkDriverConf conf = (PerfBenchmarkDriverConf) new Yaml().load(new FileInputStream(confFile));
    //since its only to run queries, we should ensure no services get started
    conf.setStartBroker(false);
    conf.setStartController(false);
    conf.setStartServer(false);
    conf.setStartZookeeper(false);
    conf.setUploadIndexes(false);
    conf.setRunQueries(true);
    conf.setConfigureResources(false);
    PerfBenchmarkDriver driver = new PerfBenchmarkDriver(conf);

    String query = args[1];

    int numTimes = Integer.parseInt(args[2]);

    for (int i = 0; i < numTimes; i++) {
      JSONObject response = driver.postQuery(query);
      System.out.println("Response:" + response);
    }
  }
}
