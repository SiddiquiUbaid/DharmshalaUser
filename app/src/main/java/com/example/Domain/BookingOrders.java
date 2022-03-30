package com.example.Domain;


public class BookingOrders {
    String hotelId, customerUid;
    String checkin;
    String checkout;
    String paymentType;
    String paymentAmount;
    String totalNights;
    String rooms, guests;
    String userName, userPhone ;
    Long millisCheckin, millisCheckout;

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public Long getMillisCheckin() {
        return millisCheckin;
    }

    public void setMillisCheckin(Long millisCheckin) {
        this.millisCheckin = millisCheckin;
    }

    public Long getMillisCheckout() {
        return millisCheckout;
    }

    public void setMillisCheckout(Long millisCheckout) {
        this.millisCheckout = millisCheckout;
    }

    public BookingOrders() {
    }


    public BookingOrders(String customerUid, String hotelId, String checkin, String checkout, String paymentType,
                         String paymentAmount, String totalNights, String rooms, String guests,
                         String userName, String userPhone, Long millisCheckin, Long millisCheckout) {

        this.customerUid = customerUid;
        this.hotelId = hotelId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.paymentType = paymentType;
        this.paymentAmount = paymentAmount;
        this.totalNights = totalNights;
        this.rooms = rooms;
        this.guests = guests;
        this.userName = userName;
        this.userPhone = userPhone;
        this.millisCheckin = millisCheckin;
        this.millisCheckout = millisCheckout;
    }




    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }




    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(String totalNights) {
        this.totalNights = totalNights;
    }




}
