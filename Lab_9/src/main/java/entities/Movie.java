package entities;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "id_movie", unique = true)
    private String idMovie;
    private String title;
    @Column(name = "release_date")
    private String releaseDate;
    private String duration;
    private double score = 0;
    private int votes = 0;

    public Movie(String idMovie, String title, String releaseDate, String duration) {
        this.idMovie = idMovie;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Movie() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", idMovie='" + idMovie + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration='" + duration + '\'' +
                ", score=" + score +
                ", votes=" + votes +
                '}';
    }
}
