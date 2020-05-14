#提供资源的服务
    ##我们模拟提供资源，所以只写了controller层
    ##对于资源的权限的授权认证，我们可以从web层，即WebSecurityConfig类中进行了配置
      我们也可以在类和方法上进行拦截，我们一般在controller上进行拦截即可，在方法上 利用hasAuthority 判断是否有这个权限
    ##我们在ResoucreServerConfigure类中行进行令牌的校验
        ### 如果我们是利用jwt的方式，可以自己进行本地的校验
        ### 如果是基于内存的方式，我们需要配置远程token校验