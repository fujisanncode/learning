package com.example.sqllearning.utils.common;

public enum ApiUrl {
  area(
      "area",
      "http://api.map.baidu.com/shangquan/forward/?qt=sub_area_list&ext=1&level=3&areacode=1&business_flag=0");

  private String name;
  private String value;

  ApiUrl(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
