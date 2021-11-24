package maroqand.uz.real_estate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ad")
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titke")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "extra_image1")
    private  String extraImage1;

    @Column(name = "extra_image2")
    private  String extraImage2;

    @Column(name = "extra_image3")
    private  String extraImage3;

    @OneToMany(mappedBy = "ad", fetch = FetchType.EAGER)
    private List<FileStorage> fileStorageList;

    @Column(name = "number_of_rooms")
    private byte numberOfRooms;

    @Column(name = "price")
    private double price;

    @Column(name = "floor_number")
    private byte floorNumber;

    @Column(name = "square")
    private int square;

    @Column(name = "phone_number")
    private String phoneNumber;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getExtraImage1() {
        return extraImage1;
    }

    public void setExtraImage1(String extraImage1) {
        this.extraImage1 = extraImage1;
    }

    public String getExtraImage2() {
        return extraImage2;
    }

    public void setExtraImage2(String extraImage2) {
        this.extraImage2 = extraImage2;
    }

    public String getExtraImage3() {
        return extraImage3;
    }

    public void setExtraImage3(String extraImage3) {
        this.extraImage3 = extraImage3;
    }

    @Transient
    public String getMainImagePath(){
        if (id == null || mainImage == null) return null;

        return "/ad-images/" + id + "/" + mainImage;
    }

    @Transient
    public String getExtraImagePath1(){
        if (id == null || extraImage1 == null) return null;

        return "/ad-images/" + id + "/" + extraImage1;
    }

    @Transient
    public String getMainImagePath2(){
        if (id == null || extraImage2 == null) return null;

        return "/ad-images/" + id + "/" + extraImage2;
    }

    @Transient
    public String getMainImagePath3(){
        if (id == null || extraImage3 == null) return null;

        return "/ad-images/" + id + "/" + extraImage3;
    }

    public List<FileStorage> getFileStorageList() {
        return fileStorageList;
    }

    public void setFileStorageList(List<FileStorage> fileStorageList) {
        this.fileStorageList = fileStorageList;
    }

    public byte getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(byte numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(byte floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
