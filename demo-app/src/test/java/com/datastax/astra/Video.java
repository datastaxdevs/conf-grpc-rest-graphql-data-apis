package com.datastax.astra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Video implements Serializable {

    /** Serial.*/
    private static final long serialVersionUID = 835129431475080845L;
    
    private UUID videoid;
    private String email;
    private String upload;
    private String url;
    private List<Integer> frames = new ArrayList<>();
    private List<String> tags= new ArrayList<>();
    private Map<String, Map<String, Integer> > formats= new HashMap<>();
    
    public Video() {}
    /**
     * Getter accessor for attribute 'videoid'.
     *
     * @return
     *       current value of 'videoid'
     */
    public UUID getVideoid() {
        return videoid;
    }
    /**
     * Setter accessor for attribute 'videoid'.
     * @param videoid
     * 		new value for 'videoid '
     */
    public void setVideoid(UUID videoid) {
        this.videoid = videoid;
    }
    /**
     * Getter accessor for attribute 'email'.
     *
     * @return
     *       current value of 'email'
     */
    public String getEmail() {
        return email;
    }
    /**
     * Setter accessor for attribute 'email'.
     * @param email
     * 		new value for 'email '
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Getter accessor for attribute 'upload'.
     *
     * @return
     *       current value of 'upload'
     */
    public String getUpload() {
        return upload;
    }
    /**
     * Setter accessor for attribute 'upload'.
     * @param upload
     * 		new value for 'upload '
     */
    public void setUpload(String upload) {
        this.upload = upload;
    }
    /**
     * Getter accessor for attribute 'url'.
     *
     * @return
     *       current value of 'url'
     */
    public String getUrl() {
        return url;
    }
    /**
     * Setter accessor for attribute 'url'.
     * @param url
     * 		new value for 'url '
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Getter accessor for attribute 'frames'.
     *
     * @return
     *       current value of 'frames'
     */
    public List<Integer> getFrames() {
        return frames;
    }
    /**
     * Setter accessor for attribute 'frames'.
     * @param frames
     * 		new value for 'frames '
     */
    public void setFrames(List<Integer> frames) {
        this.frames = frames;
    }
    /**
     * Getter accessor for attribute 'tags'.
     *
     * @return
     *       current value of 'tags'
     */
    public List<String> getTags() {
        return tags;
    }
    /**
     * Setter accessor for attribute 'tags'.
     * @param tags
     * 		new value for 'tags '
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    /**
     * Getter accessor for attribute 'formats'.
     *
     * @return
     *       current value of 'formats'
     */
    public Map<String, Map<String, Integer>> getFormats() {
        return formats;
    }
    /**
     * Setter accessor for attribute 'formats'.
     * @param formats
     * 		new value for 'formats '
     */
    public void setFormats(Map<String, Map<String, Integer>> formats) {
        this.formats = formats;
    }
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Video [videoid=" + videoid + ", email=" + email + ", upload=" + upload + ", url=" + url + ", frames=" + frames
                + ", tags=" + tags + ", formats=" + formats + "]";
    }
    
}
