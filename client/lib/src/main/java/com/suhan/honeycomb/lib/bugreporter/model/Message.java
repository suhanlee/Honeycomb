package com.suhan.honeycomb.lib.bugreporter.model;

import java.io.Serializable;

/**
 * Created by suhanlee on 15. 7. 25..
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 10L;
    /*
    key :os_version, String
  key :app_version, String
  key :model, String
  key :device, String
  key :contents, String
     */
    public String id;
    public String os_version;
    public String app_version;
    public String model;
    public String device;
    public String contents;
}
