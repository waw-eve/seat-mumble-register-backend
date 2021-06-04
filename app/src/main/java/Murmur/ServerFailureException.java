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
 * This is thrown if {@link Server.start} fails, and should generally be the cause for some concern.
 **/
public class ServerFailureException extends MurmurException
{
    public ServerFailureException()
    {
        super();
    }

    public ServerFailureException(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::Murmur::ServerFailureException";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Murmur::ServerFailureException", -1, false);
        ostr_.endSlice();
        super._writeImpl(ostr_);
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
        super._readImpl(istr_);
    }

    /** @hidden */
    public static final long serialVersionUID = 8356206704863256661L;
}