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
 * Helper class for marshaling/unmarshaling ServerList.
 **/
public final class ServerListHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, ServerPrx[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                ostr.writeProxy(v[i0]);
            }
        }
    }

    public static ServerPrx[] read(com.zeroc.Ice.InputStream istr)
    {
        final ServerPrx[] v;
        final int len0 = istr.readAndCheckSeqSize(2);
        v = new ServerPrx[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = ServerPrx.uncheckedCast(istr.readProxy());
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<ServerPrx[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, ServerPrx[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ServerListHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<ServerPrx[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            ServerPrx[] v;
            v = ServerListHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
