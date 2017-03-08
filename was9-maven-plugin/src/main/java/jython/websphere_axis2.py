import sys
import getopt
import time
import traceback
import string

host = r"{{host}}"
cell = r"{{cell}}"
cluster = r"{{cluster}}"
server = r"{{server}}"
node = r"{{node}}"
applicationName = r"{{applicationName}}"
contextRoot = r"{{contextRoot}}"
virtualHost = r"{{virtualHost}}"
sharedLibs = r"{{sharedLibs}}"
sharedDatasources = r"{{sharedDatasources}}"
#profileName = r"{{profileName}}"
parentLast = r"{{parentLast}}"
webModuleParentLast = r"{{webModuleParentLast}}"
packageFile = r"{{packageFile}}"

class WebSphere:
    def listApplications(self):
        print "[LISTADO DE APLICACIONES]", host
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print AdminApp.list()

    def restartServer(self):
        print '-'*60
        print "[REINICIANDO EL SERVIDOR]", host
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60
        try:
            if "" != cluster:
                appManager = AdminControl.queryNames('name=' + cluster + ',type=Cluster,process=dmgr,*')
                print AdminControl.invoke(appManager, 'rippleStart')
                print ""
                print '='*60
                print "[NOTIFICACION]: It takes a few minutes for the cluster to fully back running after the build finished!"
                print '='*60
                print ""
            else:
                appManager = AdminControl.queryNames('node=' + node + ',type=ApplicationManager,process=' + server + ',*')
                print AdminControl.invoke(appManager, 'restart')
        except:
            print "FALLO al reiniciar el cluster/server:"
            print '-'*10
            traceback.print_exc(file=sys.stdout)
            print '-'*10
            print "INTENTANDO ARRANCAR APLICACION DIRECTAMENTE..."
            self.startApplication()

    def startApplication(self):
        print '-'*60
        print "[INICIALIZANDO LA APLICACION ]", host, applicationName
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60
        print "SERVER Selected  = " + server
        print "NODE Selected = " + node
        print "CELL Selected  = " +  cell
        try:
            if "" == node:
                if "" == cell:
                    AdminControl.invoke('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,spec=1.0', 'startApplication','['+applicationName+']')                
                else:
                    AdminControl.invoke('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell=' + cell + ',spec=1.0', 'startApplication','['+applicationName+']')
                #appManager = AdminControl.queryNames('type=ApplicationManager,process=' + server + ',*')
                #appManager = AdminControl.queryNames('WebSphere:name=ApplicationManager,process='+ server +',version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell='+cell+',spec=1.0')
            else:
                if "" == cell:
                    AdminControl.invoke('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,node='+ node +',version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,spec=1.0','startApplication', '['+applicationName+']')                
                else:
                    AdminControl.invoke('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,node='+ node +',version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell=' + cell + ',spec=1.0','startApplication', '['+applicationName+']')
                
                #appManager = AdminControl.queryNames('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,node='+ node +',version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell='+cell+',spec=1.0')
                #('WebSphere:name=ApplicationManager,process='+ server +',platform=proxy,node='+ node +',version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell='+cell+',spec=1.0')
                #AdminControl.invoke('WebSphere:name=ApplicationManager,process=server1,platform=proxy,node=ESMAD1METWEU01Node01,version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell=ESMAD1METWEU01Node01Cell,spec=1.0', 'startApplication', '[extranet2-1_0_war]')
            #print 
            #AdminControl.invoke('WebSphere:name=ApplicationManager , process='+ server +',platform=proxy,node='+ node +',version=9.0.0.2,type=ApplicationManager , mbeanIdentifier=ApplicationManager , cell='+cell + ',spec=1.0', 'startApplication', '['+applicationName+']')
            
            #AdminControl.invoke('WebSphere:name=ApplicationManager,process=server1,platform=proxy,node=ESMAD1METWEU01Node01,version=9.0.0.2,type=ApplicationManager,mbeanIdentifier=ApplicationManager,cell=ESMAD1METWEU01Node01Cell,spec=1.0', 'startApplication', '[extranet2]')
            #AdminApplication.startApplicationOnCluster(applicationName, cluster)
        except:
            print "FALLO al iniciar la aplicacion :"
            print '-'*10
            traceback.print_exc(file=sys.stdout)
            print '-'*10

    def stopApplication(self):
        print '-'*60
        print "[PARANDO APLICACION]", host, applicationName
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60
        try:
            if "" == node:
                appManager = AdminControl.queryNames('type=ApplicationManager,process=' + server + ',*')
            else:
                appManager = AdminControl.queryNames('node=' + node + ',type=ApplicationManager,process=' + server + ',*')
            print AdminControl.invoke(appManager, 'stopApplication', applicationName)
            #AdminApplication.stopApplicationOnCluster(applicationName, cluster)
        except:
            print "FALLO al parar la aplicacion:"
            print '-'*10
            traceback.print_exc(file=sys.stdout)
            print '-'*10

    def installApplication(self):
        print '-'*60
        print "[INSTALANDO APLICACION CON SERVICIOS WEB AXIS2 1.6]", host, applicationName
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60

        
        #options = ['-deployws', '-distributeApp', '-appname', applicationName, '-server', server ]

        try:
            
            # deploy ws 
            options = ['-deployws']
            
            #options += ['-distributeApp']
            options += ['-nopreCompileJSPs']
            #options += ['-nouseMetaDataFromBinary']
            options += ['-nodeployejb']
            options += ['-server', server]
            options += ['-appname', applicationName]
            options += ['-createMBeansForResources']
            options += ['-noreloadEnabled']
            #options += ['-validateinstall', 'warn']
            #options += ['-noprocessEmbeddedConfig']
            #options += ['-filepermission','.*\.dll=755#.*\.so=755#.*\.a=755#.*\.sl=755']
            options += ['-noallowDispatchRemoteInclude']
            options += ['-noallowServiceRemoteInclude']
            #options += ['-asyncRequestDispatchType', 'DISABLED']
            #options += ['-nouseAutoLink']
            #options += ['-noenableClientModule']
            #options += ['-clientMode', 'isolated']
            options += ['-novalidateSchema']
            
            if "" != cluster:
                serverMapping = 'WebSphere:cluster=' + cluster
                options += ['-cluster', cluster, '-MapModulesToServers', [['.*','.*', serverMapping]]]
            else:
                serverMapping = 'WebSphere:server=' + server
                options += ['-MapModulesToServers', [['.*','.*', serverMapping]]]

            if "" != contextRoot:
                options += ['-contextroot', contextRoot]
                options += ['-CtxRootForWebMod', [['.*','.*', contextRoot]]]

            #if "" != profileName:
            #   options += ['-profileName', profileName]

            if "" != virtualHost:
                options += ['-MapWebModToVH', [['.*','.*', virtualHost]]]
                
                

            if "" != sharedLibs:
                libs = []
                for lib in sharedLibs.split(','):
                    libs.append(['.*','.*', lib])
                options += ['-MapSharedLibForMod', libs]
                
            if "" != sharedDatasources:
                dtsrcs = []
                for dt in sharedDatasources.split(','):
                    alias = string.replace(dt, 'jdbc/', '')
                    dtsrcs.append([applicationName,'.*',applicationName+'.war,WEB-INF/web.xml', dt,'javax.sql.DataSource',dt])
                options += ['-MapResRefToEJB', dtsrcs]

            print ""
            print "options: ", options
            print ""

            print "INSTALANDO..."
            print AdminApp.install(packageFile, options)
            
            print "INICIO MODIFICACION CLASSLOADER PARA APLICACION CON SERVICIOS WEB AXIS2 1.6 .. "
            self.modifyClassloader()
            print "FIN MODIFICACION CLASSLOADER PARA APLICACION CON SERVICIOS WEB AXIS2 1.6.. "

            print "GUARDANDO CONFIGURACION"
            AdminConfig.save()

            if "" != cluster:
                print "SYNCING"
                AdminNodeManagement.syncActiveNodes()

            result = AdminApp.isAppReady(applicationName)
            while result == "false":
                print "ESTADO:", AdminApp.getDeployStatus(applicationName)
                time.sleep(5)
                result = AdminApp.isAppReady(applicationName)
            print "INSTALADO", applicationName
            return "true"
        except:
            print "FALLO AL INSTALAR LA APLICACION: ", applicationName
            print '-'*10
            traceback.print_exc(file=sys.stdout)
            print '-'*10
            return "false"

    def modifyClassloader(self):
        print "init config parent first or last"
        deployments = AdminConfig.getid("/Deployment:" + applicationName + "/")
        deploymentObject = AdminConfig.showAttribute(deployments, "deployedObject")
        print "parent last option " + parentLast
        print "webModuleParentLast option " + webModuleParentLast
        if "true" == parentLast:
            #AdminApplication.configureClassLoaderLoadingModeForAnApplication(applicationName, "PARENT_LAST")
            classloader = AdminConfig.showAttribute(deploymentObject, "classloader")
            AdminConfig.modify(classloader, [['mode', 'PARENT_LAST']])

        if "true" == webModuleParentLast:
            modules = AdminConfig.showAttribute(deploymentObject, "modules")
            arrayModules = modules[1:len(modules)-1].split(" ")
            for module in arrayModules:
                if module.find('WebModuleDeployment') != -1:
                    AdminConfig.modify(module, [['classloaderMode', 'PARENT_LAST']])

    def uninstallApplication(self):
        print '-'*60
        print "[DESINSTALANDO LA APLICACION]", host, applicationName
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60
        try:
            print AdminApp.uninstall(applicationName)
            AdminConfig.save()
            if "" != cluster:
                AdminNodeManagement.syncActiveNodes()
        except:
            print "FALLO AL DESINSTALAR LA APLICACION: ", applicationName
            print '-'*10
            traceback.print_exc(file=sys.stdout)
            print '-'*10

    def isApplicationInstalled(self):
        return AdminApplication.checkIfAppExists(applicationName)

    def deploy(self):
        if "true" == self.isApplicationInstalled():
            self.uninstallApplication()

        if "true" == self.installApplication():
            print "no esta autorizado el reinicio del servidor, (by GREC0) por lo que procedemos a arrancar el modulo. " + applicationName
            self.startApplication(); 
            #self.restartServer()

        print '-'*60
        print "[FIN DE LA INSTALACION VIA JYTHON 2.1 /PYTHON 2.7.3]", host, applicationName
        print time.strftime("%Y-%b-%d %H:%M:%S %Z")
        print '-'*60


#-----------------------------------------------------------------
# Main
#-----------------------------------------------------------------

if __name__ == "__main__":
    methods, args = getopt.getopt(sys.argv, 'o:')
    for name, method in methods:
        if name == "-o":
            getattr(WebSphere(), method)()
