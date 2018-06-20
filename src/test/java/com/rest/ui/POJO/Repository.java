package com.rest.ui.POJO;

import java.util.Objects;

public class Repository {

    private String name;
    private String desc;
    private String mainLang;
    private String license;
    private String stars;
    private String totalCount;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMainLang() {
        return mainLang;
    }

    public void setMainLang(String mainLang) {
        this.mainLang = mainLang;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }


    //Убрал проверку на лицензию так как Github api возвращает BSD 3-Clause \"New\" or \"Revised\" License,
    // вместо BSD-3-Clause license как на сайте
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return name.equals(that.name) &&
                desc.equals(that.desc) &&
                mainLang.equals(that.mainLang) &&
                stars.equals(that.stars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, mainLang, stars);
    }

}

