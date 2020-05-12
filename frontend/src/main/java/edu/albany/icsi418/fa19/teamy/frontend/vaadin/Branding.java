package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

/**
 * Branding class
 * contains logo height and width, theme color
 */
public class Branding {

    // current branding detail
    private String logo_height = "80px";
    private String logo_width = "80px";
    private String theme_color = "#87ceeb"; // skyblue

    public Branding(String logo_height, String logo_width, String theme_color) {
        this.logo_height = logo_height;
        this.logo_width = logo_width;
        this.theme_color = theme_color;
    }

    public String getLogo_height() {
        return logo_height;
    }

    public String getLogo_width() {
        return logo_width;
    }

    public String getTheme_color() {
        return theme_color;
    }

    public void setLogo_height(String logo_height) {
        this.logo_height = logo_height;
    }

    public void setLogo_width(String logo_width) {
        this.logo_width = logo_width;
    }

    public void setTheme_color(String theme_color) {
        this.theme_color = theme_color;
    }
}
