package gg.shaded.vuei.util;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * A class loader that combines multiple class loaders into one.<br>
 * The classes loaded by this class loader are associated with this class loader,
 * i.e. Class.getClassLoader() points to this class loader.
 */
public class JoinClassLoader extends ClassLoader {

    private ClassLoader[] delegateClassLoaders;

    public JoinClassLoader (ClassLoader parent, ClassLoader... delegateClassLoaders) {
        super(parent);
        this.delegateClassLoaders = delegateClassLoaders; }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for(ClassLoader loader : delegateClassLoaders) {
            try {
                return loader.loadClass(name);
            } catch(ClassNotFoundException e) { }
        }

        return super.loadClass(name);
    }

    @Nullable
    @Override
    public URL getResource(String name) {
        for(ClassLoader loader : delegateClassLoaders) {
            URL resource = loader.getResource(name);

            if(resource != null) return resource;
        }

        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        List<URL> list = new ArrayList<>();

        for(ClassLoader loader : delegateClassLoaders) {
            Enumeration<URL> resources = loader.getResources(name);

            list.addAll(Collections.list(resources));
        }

        list.addAll(Collections.list(super.getResources(name)));

        return Collections.enumeration(list);
    }
}
