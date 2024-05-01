package fr.sciluv.application.manifiesta.manifiestaBack.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class SessionParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParticipant;
    private LocalDateTime hourOfCome;
    private LocalDateTime hourOfLeave;
    private Boolean isGuest;
    private String nameGuest;

    @OneToMany(mappedBy = "sessionParticipant")
    private Set<Vote> votes;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idSession")
    private Session session;


    //constructeurs
    public SessionParticipant() {
    }

    public SessionParticipant(User user, Session session, String role) {
        this.user = user;
        this.session = session;
        this.hourOfCome = LocalDateTime.now();
        if(role.equals("guest")){
            this.isGuest = true;
        }else{
            this.isGuest = false;
        }
        this.votes = null;
    }
    // Getters and setters

    public Long getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public LocalDateTime getHourOfCome() {
        return hourOfCome;
    }

    public void setHourOfCome(LocalDateTime hourOfCome) {
        this.hourOfCome = hourOfCome;
    }

    public LocalDateTime getHourOfLeave() {
        return hourOfLeave;
    }

    public void setHourOfLeave() {
        this.hourOfLeave = LocalDateTime.now();
    }

    public Boolean getGuest() {
        return isGuest;
    }

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    public String getNameGuest() {
        return nameGuest;
    }

    public void setNameGuest(String nameGuest) {
        this.nameGuest = nameGuest;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "SessionParticipant{" +
                "idParticipant=" + idParticipant +
                ", hourOfCome=" + hourOfCome +
                ", hourOfLeave=" + hourOfLeave +
                ", isGuest=" + isGuest +
                ", nameGuest='" + nameGuest + '\'' +
                ", user=" + user +
                ", session=" + session +
                '}';
    }
}