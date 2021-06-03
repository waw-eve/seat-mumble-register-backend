//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.5
//
// <auto-generated>
//
// Generated from file `Murmur.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Murmur;

/**
 * A connected user.
 **/
public class User implements java.lang.Cloneable,
                             java.io.Serializable
{
    /**
     * Session ID. This identifies the connection to the server.
     **/
    public int session;

    /**
     * User ID. -1 if the user is anonymous.
     **/
    public int userid;

    /**
     * Is user muted by the server?
     **/
    public boolean mute;

    /**
     * Is user deafened by the server? If true, this implies mute.
     **/
    public boolean deaf;

    /**
     * Is the user suppressed by the server? This means the user is not muted, but does not have speech privileges in the current channel.
     **/
    public boolean suppress;

    /**
     * Is the user a priority speaker?
     **/
    public boolean prioritySpeaker;

    /**
     * Is the user self-muted?
     **/
    public boolean selfMute;

    /**
     * Is the user self-deafened? If true, this implies mute.
     **/
    public boolean selfDeaf;

    /**
     * Is the User recording? (This flag is read-only and cannot be changed using setState().)
     **/
    public boolean recording;

    /**
     * Channel ID the user is in. Matches {@link Channel.id}.
     **/
    public int channel;

    /**
     * The name of the user.
     **/
    public String name;

    /**
     * Seconds user has been online.
     **/
    public int onlinesecs;

    /**
     * Average transmission rate in bytes per second over the last few seconds.
     **/
    public int bytespersec;

    /**
     * Client version. Major version in upper 16 bits, followed by 8 bits of minor version and 8 bits of patchlevel. Version 1.2.3 = 0x010203.
     **/
    public int version;

    /**
     * Client release. For official releases, this equals the version. For snapshots and git compiles, this will be something else.
     **/
    public String release;

    /**
     * Client OS.
     **/
    public String os;

    /**
     * Client OS Version.
     **/
    public String osversion;

    /**
     * Plugin Identity. This will be the user's unique ID inside the current game.
     **/
    public String identity;

    /**
     * Base64-encoded Plugin context. This is a binary blob identifying the game and team the user is on.
     *
     * The used Base64 alphabet is the one specified in RFC 2045.
     *
     * Before Mumble 1.3.0, this string was not Base64-encoded. This could cause problems for some Ice
     * implementations, such as the .NET implementation.
     *
     * If you need the exact string that is used by Mumble, you can get it by Base64-decoding this string.
     *
     * If you simply need to detect whether two users are in the same game world, string comparisons will
     * continue to work as before.
     **/
    public String context;

    /**
     * User comment. Shown as tooltip for this user.
     **/
    public String comment;

    /**
     * Client address.
     **/
    public byte[] address;

    /**
     * TCP only. True until UDP connectivity is established.
     **/
    public boolean tcponly;

    /**
     * Idle time. This is how many seconds it is since the user last spoke. Other activity is not counted.
     **/
    public int idlesecs;

    /**
     * UDP Ping Average. This is the average ping for the user via UDP over the duration of the connection.
     **/
    public float udpPing;

    /**
     * TCP Ping Average. This is the average ping for the user via TCP over the duration of the connection.
     **/
    public float tcpPing;

    public User()
    {
        this.name = "";
        this.release = "";
        this.os = "";
        this.osversion = "";
        this.identity = "";
        this.context = "";
        this.comment = "";
    }

    public User(int session, int userid, boolean mute, boolean deaf, boolean suppress, boolean prioritySpeaker, boolean selfMute, boolean selfDeaf, boolean recording, int channel, String name, int onlinesecs, int bytespersec, int version, String release, String os, String osversion, String identity, String context, String comment, byte[] address, boolean tcponly, int idlesecs, float udpPing, float tcpPing)
    {
        this.session = session;
        this.userid = userid;
        this.mute = mute;
        this.deaf = deaf;
        this.suppress = suppress;
        this.prioritySpeaker = prioritySpeaker;
        this.selfMute = selfMute;
        this.selfDeaf = selfDeaf;
        this.recording = recording;
        this.channel = channel;
        this.name = name;
        this.onlinesecs = onlinesecs;
        this.bytespersec = bytespersec;
        this.version = version;
        this.release = release;
        this.os = os;
        this.osversion = osversion;
        this.identity = identity;
        this.context = context;
        this.comment = comment;
        this.address = address;
        this.tcponly = tcponly;
        this.idlesecs = idlesecs;
        this.udpPing = udpPing;
        this.tcpPing = tcpPing;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        User r = null;
        if(rhs instanceof User)
        {
            r = (User)rhs;
        }

        if(r != null)
        {
            if(this.session != r.session)
            {
                return false;
            }
            if(this.userid != r.userid)
            {
                return false;
            }
            if(this.mute != r.mute)
            {
                return false;
            }
            if(this.deaf != r.deaf)
            {
                return false;
            }
            if(this.suppress != r.suppress)
            {
                return false;
            }
            if(this.prioritySpeaker != r.prioritySpeaker)
            {
                return false;
            }
            if(this.selfMute != r.selfMute)
            {
                return false;
            }
            if(this.selfDeaf != r.selfDeaf)
            {
                return false;
            }
            if(this.recording != r.recording)
            {
                return false;
            }
            if(this.channel != r.channel)
            {
                return false;
            }
            if(this.name != r.name)
            {
                if(this.name == null || r.name == null || !this.name.equals(r.name))
                {
                    return false;
                }
            }
            if(this.onlinesecs != r.onlinesecs)
            {
                return false;
            }
            if(this.bytespersec != r.bytespersec)
            {
                return false;
            }
            if(this.version != r.version)
            {
                return false;
            }
            if(this.release != r.release)
            {
                if(this.release == null || r.release == null || !this.release.equals(r.release))
                {
                    return false;
                }
            }
            if(this.os != r.os)
            {
                if(this.os == null || r.os == null || !this.os.equals(r.os))
                {
                    return false;
                }
            }
            if(this.osversion != r.osversion)
            {
                if(this.osversion == null || r.osversion == null || !this.osversion.equals(r.osversion))
                {
                    return false;
                }
            }
            if(this.identity != r.identity)
            {
                if(this.identity == null || r.identity == null || !this.identity.equals(r.identity))
                {
                    return false;
                }
            }
            if(this.context != r.context)
            {
                if(this.context == null || r.context == null || !this.context.equals(r.context))
                {
                    return false;
                }
            }
            if(this.comment != r.comment)
            {
                if(this.comment == null || r.comment == null || !this.comment.equals(r.comment))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(this.address, r.address))
            {
                return false;
            }
            if(this.tcponly != r.tcponly)
            {
                return false;
            }
            if(this.idlesecs != r.idlesecs)
            {
                return false;
            }
            if(this.udpPing != r.udpPing)
            {
                return false;
            }
            if(this.tcpPing != r.tcpPing)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Murmur::User");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, session);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, userid);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, mute);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, deaf);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, suppress);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, prioritySpeaker);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, selfMute);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, selfDeaf);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, recording);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, channel);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, name);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, onlinesecs);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, bytespersec);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, version);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, release);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, os);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, osversion);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, identity);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, context);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, comment);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, address);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, tcponly);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, idlesecs);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, udpPing);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, tcpPing);
        return h_;
    }

    public User clone()
    {
        User c = null;
        try
        {
            c = (User)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeInt(this.session);
        ostr.writeInt(this.userid);
        ostr.writeBool(this.mute);
        ostr.writeBool(this.deaf);
        ostr.writeBool(this.suppress);
        ostr.writeBool(this.prioritySpeaker);
        ostr.writeBool(this.selfMute);
        ostr.writeBool(this.selfDeaf);
        ostr.writeBool(this.recording);
        ostr.writeInt(this.channel);
        ostr.writeString(this.name);
        ostr.writeInt(this.onlinesecs);
        ostr.writeInt(this.bytespersec);
        ostr.writeInt(this.version);
        ostr.writeString(this.release);
        ostr.writeString(this.os);
        ostr.writeString(this.osversion);
        ostr.writeString(this.identity);
        ostr.writeString(this.context);
        ostr.writeString(this.comment);
        ostr.writeByteSeq(this.address);
        ostr.writeBool(this.tcponly);
        ostr.writeInt(this.idlesecs);
        ostr.writeFloat(this.udpPing);
        ostr.writeFloat(this.tcpPing);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.session = istr.readInt();
        this.userid = istr.readInt();
        this.mute = istr.readBool();
        this.deaf = istr.readBool();
        this.suppress = istr.readBool();
        this.prioritySpeaker = istr.readBool();
        this.selfMute = istr.readBool();
        this.selfDeaf = istr.readBool();
        this.recording = istr.readBool();
        this.channel = istr.readInt();
        this.name = istr.readString();
        this.onlinesecs = istr.readInt();
        this.bytespersec = istr.readInt();
        this.version = istr.readInt();
        this.release = istr.readString();
        this.os = istr.readString();
        this.osversion = istr.readString();
        this.identity = istr.readString();
        this.context = istr.readString();
        this.comment = istr.readString();
        this.address = istr.readByteSeq();
        this.tcponly = istr.readBool();
        this.idlesecs = istr.readInt();
        this.udpPing = istr.readFloat();
        this.tcpPing = istr.readFloat();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, User v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public User ice_read(com.zeroc.Ice.InputStream istr)
    {
        User v = new User();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<User> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, User v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<User> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(User.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final User _nullMarshalValue = new User();

    /** @hidden */
    public static final long serialVersionUID = 7235133000075702206L;
}
