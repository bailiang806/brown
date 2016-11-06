package com.happydriving.rockets.server.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mazhiqiang
 */
public class RegionGeneratorFileFormat {

    @JSONField(name = "p")
    private List<ProvinceModel> provinceModels = new ArrayList<>();

    public List<ProvinceModel> getProvinceModels() {
        return provinceModels;
    }

    public void setProvinceModels(List<ProvinceModel> provinceModels) {
        this.provinceModels = provinceModels;
    }

    public static class ProvinceModel{
        private int id;
        private String name;
        @JSONField(name = "c")
        private List<CityModel> cityModels = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityModel> getCityModels() {
            return cityModels;
        }

        public void setCityModels(List<CityModel> cityModels) {
            this.cityModels = cityModels;
        }
    }

    public static class CityModel{
        private int id;
        private String name;
        @JSONField(name = "o")
        private List<CountyModel> countyModels = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CountyModel> getCountyModels() {
            return countyModels;
        }

        public void setCountyModels(List<CountyModel> countyModels) {
            this.countyModels = countyModels;
        }
    }

    public static class CountyModel {
        private int id;
        private String name;
        @JSONField(name = "t")
        private List<TownModel> townModels = new ArrayList<>();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TownModel> getTownModels() {
            return townModels;
        }

        public void setTownModels(List<TownModel> townModels) {
            this.townModels = townModels;
        }
    }

    public static class TownModel{
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
