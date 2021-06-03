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

public enum UserInfo implements java.io.Serializable
{
    UserName(0),
    UserEmail(1),
    UserComment(2),
    UserHash(3),
    UserPassword(4),
    UserLastActive(5),
    UserKDFIterations(6);

    public int value()
    {
        return _value;
    }

    public static UserInfo valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return UserName;
        case 1:
            return UserEmail;
        case 2:
            return UserComment;
        case 3:
            return UserHash;
        case 4:
            return UserPassword;
        case 5:
            return UserLastActive;
        case 6:
            return UserKDFIterations;
        }
        return null;
    }

    private UserInfo(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 6);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, UserInfo v)
    {
        if(v == null)
        {
            ostr.writeEnum(Murmur.UserInfo.UserName.value(), 6);
        }
        else
        {
            ostr.writeEnum(v.value(), 6);
        }
    }

    public static UserInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(6);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<UserInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, UserInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<UserInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static UserInfo validate(int v)
    {
        final UserInfo e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
