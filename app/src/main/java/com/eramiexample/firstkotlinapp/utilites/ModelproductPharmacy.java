package com.eramiexample.firstkotlinapp.utilites;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelproductPharmacy {


    private String type;
    private List<DataBean> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {


        private CompanyBean company;
        private String title;
        private List<MedicinesBean> medicines;

        public DataBean(CompanyBean company, List<MedicinesBean> medicines) {
            this.company = company;
            this.medicines = medicines;
        }

        private DataBean(Parcel in) {
            company = in.readParcelable(CompanyBean.class.getClassLoader());
            title = in.readString();
            medicines = in.createTypedArrayList(MedicinesBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public List<MedicinesBean> getMedicines() {
            return medicines;
        }

        public void setMedicines(List<MedicinesBean> medicines) {
            this.medicines = medicines;
        }



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(company, i);
            parcel.writeString(title);
            parcel.writeTypedList(medicines);
        }

        public static class CompanyBean implements Parcelable{

            private String id;
            private String name;
            private String avatar;

            public CompanyBean(String name) {
                this.name = name;
            }

            protected CompanyBean(Parcel in) {
                id = in.readString();
                name = in.readString();
                avatar = in.readString();
            }

            public static final Creator<CompanyBean> CREATOR = new Creator<CompanyBean>() {
                @Override
                public CompanyBean createFromParcel(Parcel in) {
                    return new CompanyBean(in);
                }

                @Override
                public CompanyBean[] newArray(int size) {
                    return new CompanyBean[size];
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(id);
                parcel.writeString(name);
                parcel.writeString(avatar);
            }
        }

        public static class MedicinesBean implements Parcelable{
            private String id;
            private int quantity;
            private String price;
            private String name;
            private String code;
            private String img;
            private String about;
            private String side_effects;

            public MedicinesBean(int quantity) {
                this.quantity = quantity;
            }

            protected MedicinesBean(Parcel in) {
                id = in.readString();
                price = in.readString();
                name = in.readString();
                code = in.readString();
                img = in.readString();
                about = in.readString();
                quantity = in.readInt();
                side_effects = in.readString();
            }

            public static final Creator<MedicinesBean> CREATOR = new Creator<MedicinesBean>() {
                @Override
                public MedicinesBean createFromParcel(Parcel in) {
                    return new MedicinesBean(in);
                }

                @Override
                public MedicinesBean[] newArray(int size) {
                    return new MedicinesBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getAbout() {
                return about;
            }

            public void setAbout(String about) {
                this.about = about;
            }

            public String getSide_effects() {
                return side_effects;
            }

            public void setSide_effects(String side_effects) {
                this.side_effects = side_effects;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(id);
                parcel.writeString(price);
                parcel.writeString(name);
                parcel.writeString(code);
                parcel.writeString(img);
                parcel.writeString(about);
                parcel.writeInt(quantity);
                parcel.writeString(side_effects);
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }
    }
}