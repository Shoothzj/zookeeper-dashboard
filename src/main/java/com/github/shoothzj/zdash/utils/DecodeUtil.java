package com.github.shoothzj.zdash.utils;

import com.github.shoothzj.zdash.module.DecodeComponent;
import com.github.shoothzj.zdash.module.DecodeNamespace;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.bookkeeper.mledger.proto.MLDataFormats;

import java.nio.charset.StandardCharsets;

public class DecodeUtil {

    public static String decodeData(byte[] data, String component, String namespace) throws Exception {
        DecodeComponent decodeComponent = DecodeComponent.valueOf(component);
        switch (decodeComponent) {
            case Pulsar:
                return decodePulsarData(data, namespace);
            default:
                return new String(data, StandardCharsets.UTF_8);
        }
    }

    public static String decodePulsarData(byte[] data, String namespace) throws Exception {
        DecodeNamespace decodeNamespace = DecodeNamespace.valueOf(namespace);
        switch (decodeNamespace) {
            case ManagedLedgerTopic:
                return decodePulsarManagedLedgerTopicData(data);
            case ManagedManagedLedgerSubscription:
                return decodePulsarManagedLedgerSubscriptionData(data);
            default:
                return new String(data, StandardCharsets.UTF_8);
        }
    }

    public static String decodePulsarManagedLedgerTopicData(byte[] data) throws InvalidProtocolBufferException {
        MLDataFormats.ManagedLedgerInfo managedLedgerInfo = MLDataFormats.ManagedLedgerInfo.parseFrom(data);
        return managedLedgerInfo.toString();
    }

    public static String decodePulsarManagedLedgerSubscriptionData(byte[] data) throws InvalidProtocolBufferException {
        MLDataFormats.ManagedCursorInfo managedCursorInfo = MLDataFormats.ManagedCursorInfo.parseFrom(data);
        return managedCursorInfo.toString();
    }

}
