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
 * User and subchannel state. Read-only.
 **/
public class Tree extends com.zeroc.Ice.Value
{
    public Tree()
    {
        this.c = new Channel();
    }

    public Tree(Channel c, Tree[] children, User[] users)
    {
        this.c = c;
        this.children = children;
        this.users = users;
    }

    /**
     * Channel definition of current channel.
     **/
    public Channel c;

    /**
     * List of subchannels.
     **/
    public Tree[] children;

    /**
     * Users in this channel.
     **/
    public User[] users;

    public Tree clone()
    {
        return (Tree)super.clone();
    }

    public static String ice_staticId()
    {
        return "::Murmur::Tree";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = -6734201072128765534L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, true);
        Channel.ice_write(ostr_, c);
        TreeListHelper.write(ostr_, children);
        UserListHelper.write(ostr_, users);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        c = Channel.ice_read(istr_);
        children = TreeListHelper.read(istr_);
        users = UserListHelper.read(istr_);
        istr_.endSlice();
    }
}
