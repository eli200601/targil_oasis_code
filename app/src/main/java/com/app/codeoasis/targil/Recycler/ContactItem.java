package com.app.codeoasis.targil.Recycler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eliran.alon on 11-May-17.
 */

public class ContactItem implements Parcelable {

    private String id;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String mobile;
    private String home;
    private String office;

    public ContactItem(String id, String name, String email, String address, String gender, String mobile, String home, String office) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }

    public ContactItem() {
    }

    public static final Creator<ContactItem> CREATOR = new Creator<ContactItem>() {
        @Override
        public ContactItem createFromParcel(Parcel in) {
            return new ContactItem(in);
        }

        @Override
        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(gender);
        parcel.writeString(mobile);
        parcel.writeString(home);
        parcel.writeString(office);
    }

    protected ContactItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        address = in.readString();
        gender = in.readString();
        mobile = in.readString();
        home = in.readString();
        office = in.readString();
    }
}
