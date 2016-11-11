package com.youtube.sorcjc.tabdeportes.model;

import com.youtube.sorcjc.tabdeportes.io.response.ThumbnailImages;

import java.util.ArrayList;

public class Post {
/*
    "id": 13924,
    "type": "post",
    "slug": "nolan-arenado-gana-otro-guante-de-oro",
    "url": "http://tabdeportes.news/2016/11/08/nolan-arenado-gana-otro-guante-de-oro/",
    "status": "publish",
    "title": "Nolan Arenado gana otro Guante de Oro",
    "title_plain": "Nolan Arenado gana otro Guante de Oro",
    "content": "<div id=\"fb-root\"></div>\n<p>El tercera base de ascendencia puertorriqueña, Nolan Arenado, de los Colorado Rockies, ganó esta noche su cuarto guante de oro en la Liga Nacional.<br />\nArenado desde el 2013 ha sido &#8220;dueño y señor&#8221; del premio al mejor jugador defensivo en la tercera base, demostrando sus quilates en la defensa. <script type=\"text/javascript\">\n    google_ad_client = \"ca-pub-9365600681688872\";\n    google_ad_slot = \"7335463544\";\n    google_ad_width = 300;\n    google_ad_height = 250;\n</script><br />\n<!-- Medio del texto --><br />\n<script type=\"text/javascript\" src=\"//pagead2.googlesyndication.com/pagead/show_ads.js\">\n</script><br />\nEn el 2016, Arenado, cometió solo 13 errores en 160 juegos para un<br />\n.973%.</p>\n<div class=\"post-embed\">\n<blockquote class=\"twitter-tweet\" data-width=\"500\">\n<p lang=\"en\" dir=\"ltr\">4 seasons, 4 Gold Gloves!</p>\n<p>CONGRATULATIONS to Nolan Arenado on another <a href=\"https://twitter.com/hashtag/GoldGlove?src=hash\">#GoldGlove</a>!<br /> <a href=\"https://twitter.com/hashtag/GoldenNolan?src=hash\">#GoldenNolan</a> 🏆🏆🏆🏆 <a href=\"https://t.co/ceSLYh8j8o\">pic.twitter.com/ceSLYh8j8o</a></p>\n<p>&mdash; Colorado Rockies (@Rockies) <a href=\"https://twitter.com/Rockies/status/796158536845824000\">November 9, 2016</a></p></blockquote>\n<p><script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script></div>\n<div id=\"themify_builder_content-13924\" data-postid=\"13924\" class=\"themify_builder_content themify_builder_content-13924 themify_builder themify_builder_front\">\n\t\n\t\n</div>\n<!-- /themify_builder_content -->",
    "excerpt": "<p>El tercera base de ascendencia puertorriqueña, Nolan Arenado, de los Colorado Rockies, ganó esta noche su cuarto guante de oro en la Liga Nacional. Arenado desde el 2013 ha sido &#8220;dueño y señor&#8221; del premio al mejor jugador defensivo en la tercera base, demostrando sus quilates en la defensa. En el 2016, Arenado, cometió solo(&#8230;)</p>\n",
    "date": "2016-11-08 21:13:21",
*/

    private int id;
    private String type;
    private String slug;
    private String url;
    private String status;
    private String title;
    private String content;
    private String excerpt;
    private String date;
    private ArrayList<Category> categories;
    private ThumbnailImages thumbnail_images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getCategoryList() {
        String list = "";

        for (Category category : categories) {
            list += category.getTitle() + " / ";
        }

        return  list;
    }

    public ThumbnailImages getThumbnail_images() {
        return thumbnail_images;
    }

    public void setThumbnail_images(ThumbnailImages thumbnail_images) {
        this.thumbnail_images = thumbnail_images;
    }
}
