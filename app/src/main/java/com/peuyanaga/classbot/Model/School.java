package com.peuyanaga.classbot.Model;

import com.peuyanaga.classbot.Service.AppService;

/**
 * Created by DELL on 2020-05-01.
 */

public class School {
    private int schoolId;
    private String schoolName, type, phase, physicalAddress, postalAddress,
    urbanruralc, sector, specialization, examDepartment, examCenter, longitude, latitude,
    magesterialDistrict, districtMunicipalityName, localMunicipalityName, circuit, wardId,
    districtId, townVillage, suburb, townCity;
    public School(int schoolId, String schoolName, String type, String phase, String physicalAddress, String postalAddress, String urbanruralc, String sector, String specialization, String examDepartment, String examCenter, String longitude, String latitude, String magesterialDistrict, String districtMunicipalityName, String localMunicipalityName, String circuit, String wardId, String districtId, String townVillage, String suburb, String townCity) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.type = type;
        this.phase = phase;
        this.physicalAddress = physicalAddress;
        this.postalAddress = postalAddress;
        this.urbanruralc = urbanruralc;
        this.sector = sector;
        this.specialization = specialization;
        this.examDepartment = examDepartment;
        this.examCenter = examCenter;
        this.longitude = longitude;
        this.latitude = latitude;
        this.magesterialDistrict = magesterialDistrict;
        this.districtMunicipalityName = districtMunicipalityName;
        this.localMunicipalityName = localMunicipalityName;
        this.circuit = circuit;
        this.wardId = wardId;
        this.districtId = districtId;
        this.townVillage = townVillage;
        this.suburb = suburb;
        this.townCity = townCity;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getUrbanruralc() {
        return urbanruralc;
    }

    public void setUrbanruralc(String urbanruralc) {
        this.urbanruralc = urbanruralc;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExamDepartment() {
        return examDepartment;
    }

    public void setExamDepartment(String examDepartment) {
        this.examDepartment = examDepartment;
    }

    public String getExamCenter() {
        return examCenter;
    }

    public void setExamCenter(String examCenter) {
        this.examCenter = examCenter;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMagesterialDistrict() {
        return magesterialDistrict;
    }

    public void setMagesterialDistrict(String magesterialDistrict) {
        this.magesterialDistrict = magesterialDistrict;
    }

    public String getDistrictMunicipalityName() {
        return districtMunicipalityName;
    }

    public void setDistrictMunicipalityName(String districtMunicipalityName) {
        this.districtMunicipalityName = districtMunicipalityName;
    }

    public String getLocalMunicipalityName() {
        return localMunicipalityName;
    }

    public void setLocalMunicipalityName(String localMunicipalityName) {
        this.localMunicipalityName = localMunicipalityName;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTownVillage() {
        return townVillage;
    }

    public void setTownVillage(String townVillage) {
        this.townVillage = townVillage;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String toString(){
        return (schoolName + " ( " + townCity + " )");
    }
}
