package com.mongodb.app.collection;

import java.util.Date;

/**
 * Grade collection.
 *
 * @author nico.arianto
 */
public class Grade {
    private Date date;
    private String grade;
    private Integer score;

    /**
     * Returns a date.
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets a date.
     *
     * @param date date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns a grade.
     *
     * @return grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets a grade.
     *
     * @param grade grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Returns a score.
     *
     * @return score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Sets a score.
     *
     * @param score score
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}