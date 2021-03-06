package com.hpe.apppulse.thinkasync.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Miroslaw Stanek on 08.08.15.
 */
public class UserJson implements Parcelable {
    String login;
    long id;
    String avatar_url;
    String gravatar_id;
    String url;
    String html_url;
    String followers_url;
    String following_url;
    String gists_url;
    String starred_url;
    String subscriptions_url;
    String organizations_url;
    String repos_url;
    String events_url;
    String received_events_url;
    String type;
    boolean site_admin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserJson user = (UserJson) o;

        if (id != user.id) return false;
        if (site_admin != user.site_admin) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (avatar_url != null ? !avatar_url.equals(user.avatar_url) : user.avatar_url != null)
            return false;
        if (gravatar_id != null ? !gravatar_id.equals(user.gravatar_id) : user.gravatar_id != null)
            return false;
        if (url != null ? !url.equals(user.url) : user.url != null) return false;
        if (html_url != null ? !html_url.equals(user.html_url) : user.html_url != null)
            return false;
        if (followers_url != null ? !followers_url.equals(user.followers_url) : user.followers_url != null)
            return false;
        if (following_url != null ? !following_url.equals(user.following_url) : user.following_url != null)
            return false;
        if (gists_url != null ? !gists_url.equals(user.gists_url) : user.gists_url != null)
            return false;
        if (starred_url != null ? !starred_url.equals(user.starred_url) : user.starred_url != null)
            return false;
        if (subscriptions_url != null ? !subscriptions_url.equals(user.subscriptions_url) : user.subscriptions_url != null)
            return false;
        if (organizations_url != null ? !organizations_url.equals(user.organizations_url) : user.organizations_url != null)
            return false;
        if (repos_url != null ? !repos_url.equals(user.repos_url) : user.repos_url != null)
            return false;
        if (events_url != null ? !events_url.equals(user.events_url) : user.events_url != null)
            return false;
        if (received_events_url != null ? !received_events_url.equals(user.received_events_url) : user.received_events_url != null)
            return false;
        return !(type != null ? !type.equals(user.type) : user.type != null);

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (avatar_url != null ? avatar_url.hashCode() : 0);
        result = 31 * result + (gravatar_id != null ? gravatar_id.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (html_url != null ? html_url.hashCode() : 0);
        result = 31 * result + (followers_url != null ? followers_url.hashCode() : 0);
        result = 31 * result + (following_url != null ? following_url.hashCode() : 0);
        result = 31 * result + (gists_url != null ? gists_url.hashCode() : 0);
        result = 31 * result + (starred_url != null ? starred_url.hashCode() : 0);
        result = 31 * result + (subscriptions_url != null ? subscriptions_url.hashCode() : 0);
        result = 31 * result + (organizations_url != null ? organizations_url.hashCode() : 0);
        result = 31 * result + (repos_url != null ? repos_url.hashCode() : 0);
        result = 31 * result + (events_url != null ? events_url.hashCode() : 0);
        result = 31 * result + (received_events_url != null ? received_events_url.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (site_admin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", gravatar_id='" + gravatar_id + '\'' +
                ", url='" + url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", followers_url='" + followers_url + '\'' +
                ", following_url='" + following_url + '\'' +
                ", gists_url='" + gists_url + '\'' +
                ", starred_url='" + starred_url + '\'' +
                ", subscriptions_url='" + subscriptions_url + '\'' +
                ", organizations_url='" + organizations_url + '\'' +
                ", repos_url='" + repos_url + '\'' +
                ", events_url='" + events_url + '\'' +
                ", received_events_url='" + received_events_url + '\'' +
                ", type='" + type + '\'' +
                ", site_admin=" + site_admin +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeLong(this.id);
        dest.writeString(this.avatar_url);
        dest.writeString(this.gravatar_id);
        dest.writeString(this.url);
        dest.writeString(this.html_url);
        dest.writeString(this.followers_url);
        dest.writeString(this.following_url);
        dest.writeString(this.gists_url);
        dest.writeString(this.starred_url);
        dest.writeString(this.subscriptions_url);
        dest.writeString(this.organizations_url);
        dest.writeString(this.repos_url);
        dest.writeString(this.events_url);
        dest.writeString(this.received_events_url);
        dest.writeString(this.type);
        dest.writeByte(site_admin ? (byte) 1 : (byte) 0);
    }

    public UserJson() {
    }

    protected UserJson(Parcel in) {
        this.login = in.readString();
        this.id = in.readLong();
        this.avatar_url = in.readString();
        this.gravatar_id = in.readString();
        this.url = in.readString();
        this.html_url = in.readString();
        this.followers_url = in.readString();
        this.following_url = in.readString();
        this.gists_url = in.readString();
        this.starred_url = in.readString();
        this.subscriptions_url = in.readString();
        this.organizations_url = in.readString();
        this.repos_url = in.readString();
        this.events_url = in.readString();
        this.received_events_url = in.readString();
        this.type = in.readString();
        this.site_admin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<UserJson> CREATOR = new Parcelable.Creator<UserJson>() {
        public UserJson createFromParcel(Parcel source) {
            return new UserJson(source);
        }

        public UserJson[] newArray(int size) {
            return new UserJson[size];
        }
    };
}
