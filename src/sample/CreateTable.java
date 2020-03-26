package sample;

public class CreateTable {
    int id, idDetail;
    String band, song;

    public CreateTable(int id, String band, String song, int idDetail) {
        this.id = id;
        this.band = band;
        this.song = song;
        this.idDetail = idDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }


}
