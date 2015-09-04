package it.cosenonjaviste.core;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class ParcelableTester {
    public static <T extends Parcelable> void check(T parcelable, Parcelable.Creator<T> creator) {
        Parcel parcel = createParcel();

        parcelable.writeToParcel(parcel, 0);

        T loadedData = creator.createFromParcel(parcel);

        Gson gson = new Gson();
        String s1 = gson.toJson(parcelable);
        String s2 = gson.toJson(loadedData);

        assertThat(s2).isEqualTo(s1);

        assertThat(parcelable.describeContents()).isEqualTo(0);
        assertThat(creator.newArray(2)).hasSize(2);
    }

    @NonNull public static Parcel createParcel() {
        Parcel parcel = Mockito.mock(Parcel.class);

        LinkedList<Object> parcelData = new LinkedList<>();

        Answer writeAnswer = invocation -> {
            parcelData.add(invocation.getArguments()[0]);
            return null;
        };
        Answer<Object> readAnswer = invocation -> parcelData.removeFirst();

        doAnswer(writeAnswer).when(parcel).writeInt(anyInt());
        when(parcel.readInt()).thenAnswer(readAnswer);

        doAnswer(writeAnswer).when(parcel).writeLong(anyLong());
        when(parcel.readLong()).thenAnswer(readAnswer);

        doAnswer(writeAnswer).when(parcel).writeString(anyString());
        when(parcel.readString()).thenAnswer(readAnswer);

        doAnswer(writeAnswer).when(parcel).writeByte(anyByte());
        when(parcel.readByte()).thenAnswer(readAnswer);

        doAnswer(invocation -> {
            List<Parcelable> list = (List<Parcelable>) invocation.getArguments()[0];
            writeList(parcelData, invocation, list);
            return null;
        }).when(parcel).writeList(any());
        doAnswer(invocation -> {
            List<Parcelable> list = (List<Parcelable>) invocation.getArguments()[0];
            readList(parcelData, invocation, list);
            return null;
        }).when(parcel).readList(any(), any());

        doAnswer(invocation -> {
            Parcelable[] parcelables = (Parcelable[]) invocation.getArguments()[0];
            writeList(parcelData, invocation, parcelables == null ? null : Arrays.asList(parcelables));
            return null;
        }).when(parcel).writeParcelableArray(any(), anyInt());
        doAnswer(invocation -> {
            List<Parcelable> list = new ArrayList<>();
            readList(parcelData, invocation, list);
            return list.toArray(new Parcelable[list.size()]);
        }).when(parcel).readParcelableArray(any());

        doAnswer(invocation -> {
            Parcelable parcelable = (Parcelable) invocation.getArguments()[0];
            writeParcelable(parcelData, parcelable, (Parcel) invocation.getMock());
            return null;
        }).when(parcel).writeParcelable(any(), anyInt());
        when(parcel.readParcelable(any())).thenAnswer(invocation -> readParcelable(parcelData, (Parcel) invocation.getMock()));

        return parcel;
    }

    private static void readList(LinkedList<Object> parcelData, InvocationOnMock invocation, List<Parcelable> list) {
        int size = (int) parcelData.removeFirst();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                list.add(readParcelable(parcelData, (Parcel) invocation.getMock()));
            }
        }
    }

    private static void writeList(LinkedList<Object> parcelData, InvocationOnMock invocation, List<Parcelable> list) {
        if (list == null) {
            parcelData.add(-1);
        } else {
            parcelData.add(list.size());
            for (Parcelable item : list) {
                writeParcelable(parcelData, item, (Parcel) invocation.getMock());
            }
        }
    }

    private static Parcelable readParcelable(LinkedList<Object> parcelData, Parcel parcel1) {
        Class<? extends Parcelable> parcelableClass = (Class<? extends Parcelable>) parcelData.removeFirst();
        if (parcelableClass == null) {
            return null;
        } else {
            Parcelable.Creator<Parcelable> parcelableCreator = readParcelableCreator(parcelableClass);
            parcelableCreator.newArray(2);
            return parcelableCreator.createFromParcel(parcel1);
        }
    }

    private static void writeParcelable(LinkedList<Object> parcelData, Parcelable parcelable, Parcel parcel1) {
        if (parcelable == null) {
            parcelData.add(null);
        } else {
            parcelable.describeContents();
            parcelData.add(parcelable.getClass());
            parcelable.writeToParcel(parcel1, 0);
        }
    }

    private static <T extends Parcelable> Parcelable.Creator<T> readParcelableCreator(Class c) {
        Parcelable.Creator<T> creator;
        try {
            Field f = c.getField("CREATOR");
            creator = (Parcelable.Creator) f.get(null);
        } catch (IllegalAccessException e) {
            throw new BadParcelableException(
                    "IllegalAccessException when unmarshalling: " + c.getName());
        } catch (ClassCastException e) {
            throw new BadParcelableException("Parcelable protocol requires a "
                    + "Parcelable.Creator object called "
                    + " CREATOR on class " + c.getName());
        } catch (NoSuchFieldException e) {
            throw new BadParcelableException("Parcelable protocol requires a "
                    + "Parcelable.Creator object called "
                    + " CREATOR on class " + c.getName());
        } catch (NullPointerException e) {
            throw new BadParcelableException("Parcelable protocol requires "
                    + "the CREATOR object to be static on class " + c.getName());
        }
        if (creator == null) {
            throw new BadParcelableException("Parcelable protocol requires a "
                    + "Parcelable.Creator object called "
                    + " CREATOR on class " + c.getName());
        }

        return creator;
    }
}
