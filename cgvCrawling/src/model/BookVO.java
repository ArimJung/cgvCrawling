package model;

public class BookVO {
   private int bid; // pk
   private int cid; // cgv pk(외래키)
   private String mid;  // member pk(외래키)
   private String title; // 영화제목
   private String image; // 영화포스터
   private String genre; // 영화장르
   private int bookCnt; // 예매횟수
   
   public int getBid() {
      return bid;
   }
   public void setBid(int bid) {
      this.bid = bid;
   }
   public int getCid() {
      return cid;
   }
   public void setCid(int cid) {
      this.cid = cid;
   }
   public String getMid() {
      return mid;
   }
   public void setMid(String mid) {
      this.mid = mid;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getImage() {
      return image;
   }
   public void setImage(String image) {
      this.image = image;
   }
   public String getGenre() {
      return genre;
   }
   public void setGenre(String genre) {
      this.genre = genre;
   }
   public int getBookCnt() {
      return bookCnt;
   }
   public void setBookCnt(int bookCnt) {
      this.bookCnt = bookCnt;
   }
   @Override
   public String toString() {
      return "BookVO [bid=" + bid + ", cid=" + cid + ", mid=" + mid + ", title=" + title + ", image=" + image
            + ", genre=" + genre + ", bookCnt=" + bookCnt + "]";
   }




}