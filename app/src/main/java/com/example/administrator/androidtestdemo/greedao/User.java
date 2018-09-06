package com.example.administrator.androidtestdemo.greedao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class User implements Parcelable {



    /**
     * @Entity 标识实体类，GreenDao会映射成sqlite中的一个表
     *@Id 标识主键
     *@Property 标识该属性在表中对应的列名称
     *@Transient 标识该属性将不会映射到表中，也就是没有这列
     */
    @Id(autoincrement = true)
    private Long id;
    private int memberSex;//性别
    private String memberLastX;
    //X币
    @Property(nameInDb = "sex")
    private String memberNickname;//昵称
     private String memberIcon;//头像地址链接
     private String memberMobile;//手机号
     private int memberId;//用户ID
     @Transient
     private String memberDetailAddr;//用户的详细地址
     private String memberLastExperience;//用户经验值
     private String memberLevelName;//用户等级昵称
     private long memberBirthday;//用户生日
     private String memberProvince;//用户所在地
    @Generated(hash = 802712912)
    public User(Long id, int memberSex, String memberLastX, String memberNickname,
            String memberIcon, String memberMobile, int memberId,
            String memberLastExperience, String memberLevelName,
            long memberBirthday, String memberProvince) {
        this.id = id;
        this.memberSex = memberSex;
        this.memberLastX = memberLastX;
        this.memberNickname = memberNickname;
        this.memberIcon = memberIcon;
        this.memberMobile = memberMobile;
        this.memberId = memberId;
        this.memberLastExperience = memberLastExperience;
        this.memberLevelName = memberLevelName;
        this.memberBirthday = memberBirthday;
        this.memberProvince = memberProvince;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getMemberSex() {
        return this.memberSex;
    }
    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }
    public String getMemberLastX() {
        return this.memberLastX;
    }
    public void setMemberLastX(String memberLastX) {
        this.memberLastX = memberLastX;
    }
    public String getMemberNickname() {
        return this.memberNickname;
    }
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }
    public String getMemberIcon() {
        return this.memberIcon;
    }
    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon;
    }
    public String getMemberMobile() {
        return this.memberMobile;
    }
    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }
    public int getMemberId() {
        return this.memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public String getMemberLastExperience() {
        return this.memberLastExperience;
    }
    public void setMemberLastExperience(String memberLastExperience) {
        this.memberLastExperience = memberLastExperience;
    }
    public String getMemberLevelName() {
        return this.memberLevelName;
    }
    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }
    public long getMemberBirthday() {
        return this.memberBirthday;
    }
    public void setMemberBirthday(long memberBirthday) {
        this.memberBirthday = memberBirthday;
    }
    public String getMemberProvince() {
        return this.memberProvince;
    }
    public void setMemberProvince(String memberProvince) {
        this.memberProvince = memberProvince;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.memberSex);
        dest.writeString(this.memberLastX);
        dest.writeString(this.memberNickname);
        dest.writeString(this.memberIcon);
        dest.writeString(this.memberMobile);
        dest.writeInt(this.memberId);
        dest.writeString(this.memberDetailAddr);
        dest.writeString(this.memberLastExperience);
        dest.writeString(this.memberLevelName);
        dest.writeLong(this.memberBirthday);
        dest.writeString(this.memberProvince);
    }

    protected User(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.memberSex = in.readInt();
        this.memberLastX = in.readString();
        this.memberNickname = in.readString();
        this.memberIcon = in.readString();
        this.memberMobile = in.readString();
        this.memberId = in.readInt();
        this.memberDetailAddr = in.readString();
        this.memberLastExperience = in.readString();
        this.memberLevelName = in.readString();
        this.memberBirthday = in.readLong();
        this.memberProvince = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
