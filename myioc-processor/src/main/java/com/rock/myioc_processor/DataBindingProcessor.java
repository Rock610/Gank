package com.rock.myioc_processor;

import com.google.auto.service.AutoService;
import com.rock.module_annotation.DataBindingModule;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.rock.module_annotation.DataBindingModule")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataBindingProcessor extends AbstractProcessor {

    private Elements mElementUtils; //基于元素进行操作的工具方法
    private Filer mFileCreator;     //代码创建者
    private Messager mMessager;     //日志，提示者，提示错误、警告

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(DataBindingModule.class);
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
        }
        return false;
    }


    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFileCreator = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }
}
