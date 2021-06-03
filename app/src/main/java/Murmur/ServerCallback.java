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
 * Callback interface for servers. You can supply an implementation of this to receive notification
 * messages from the server.
 * If an added callback ever throws an exception or goes away, it will be automatically removed.
 * Please note that all callbacks are done asynchronously; murmur does not wait for the callback to
 * complete before continuing processing.
 * Note that callbacks are removed when a server is stopped, so you should have a callback for
 * {@link MetaCallback.started} which calls {@link Server.addCallback}.
 *
 * @see MetaCallback
 * @see Server.addCallback
 **/
public interface ServerCallback extends com.zeroc.Ice.Object
{
    /**
     * Called when a user connects to the server.
     * @param state State of connected user.
     * @param current The Current object for the invocation.
     **/
    void userConnected(User state, com.zeroc.Ice.Current current);

    /**
     * Called when a user disconnects from the server. The user has already been removed, so you can no longer use methods like {@link Server.getState}
     * to retrieve the user's state.
     * @param state State of disconnected user.
     * @param current The Current object for the invocation.
     **/
    void userDisconnected(User state, com.zeroc.Ice.Current current);

    /**
     * Called when a user state changes. This is called if the user moves, is renamed, is muted, deafened etc.
     * @param state New state of user.
     * @param current The Current object for the invocation.
     **/
    void userStateChanged(User state, com.zeroc.Ice.Current current);

    /**
     * Called when user writes a text message
     * @param state the User sending the message
     * @param message the TextMessage the user has sent
     * @param current The Current object for the invocation.
     **/
    void userTextMessage(User state, TextMessage message, com.zeroc.Ice.Current current);

    /**
     * Called when a new channel is created.
     * @param state State of new channel.
     * @param current The Current object for the invocation.
     **/
    void channelCreated(Channel state, com.zeroc.Ice.Current current);

    /**
     * Called when a channel is removed. The channel has already been removed, you can no longer use methods like {@link Server.getChannelState}
     * @param state State of removed channel.
     * @param current The Current object for the invocation.
     **/
    void channelRemoved(Channel state, com.zeroc.Ice.Current current);

    /**
     * Called when a new channel state changes. This is called if the channel is moved, renamed or if new links are added.
     * @param state New state of channel.
     * @param current The Current object for the invocation.
     **/
    void channelStateChanged(Channel state, com.zeroc.Ice.Current current);

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::Murmur::ServerCallback"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Murmur::ServerCallback";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_userConnected(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        User iceP_state;
        iceP_state = User.ice_read(istr);
        inS.endReadParams();
        obj.userConnected(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_userDisconnected(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        User iceP_state;
        iceP_state = User.ice_read(istr);
        inS.endReadParams();
        obj.userDisconnected(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_userStateChanged(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        User iceP_state;
        iceP_state = User.ice_read(istr);
        inS.endReadParams();
        obj.userStateChanged(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_userTextMessage(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        User iceP_state;
        TextMessage iceP_message;
        iceP_state = User.ice_read(istr);
        iceP_message = TextMessage.ice_read(istr);
        inS.endReadParams();
        obj.userTextMessage(iceP_state, iceP_message, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_channelCreated(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        Channel iceP_state;
        iceP_state = Channel.ice_read(istr);
        inS.endReadParams();
        obj.channelCreated(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_channelRemoved(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        Channel iceP_state;
        iceP_state = Channel.ice_read(istr);
        inS.endReadParams();
        obj.channelRemoved(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_channelStateChanged(ServerCallback obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        Channel iceP_state;
        iceP_state = Channel.ice_read(istr);
        inS.endReadParams();
        obj.channelStateChanged(iceP_state, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "channelCreated",
        "channelRemoved",
        "channelStateChanged",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "userConnected",
        "userDisconnected",
        "userStateChanged",
        "userTextMessage"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_channelCreated(this, in, current);
            }
            case 1:
            {
                return _iceD_channelRemoved(this, in, current);
            }
            case 2:
            {
                return _iceD_channelStateChanged(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 7:
            {
                return _iceD_userConnected(this, in, current);
            }
            case 8:
            {
                return _iceD_userDisconnected(this, in, current);
            }
            case 9:
            {
                return _iceD_userStateChanged(this, in, current);
            }
            case 10:
            {
                return _iceD_userTextMessage(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
