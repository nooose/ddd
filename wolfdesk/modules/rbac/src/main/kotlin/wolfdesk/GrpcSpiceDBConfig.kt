package wolfdesk

import com.authzed.api.v1.PermissionsServiceGrpc
import com.authzed.grpcutil.BearerToken
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
internal class GrpcSpiceDBConfig {

    @Bean
    fun permissionGrpcClient(): PermissionsServiceGrpc.PermissionsServiceBlockingStub {
        val channel = io.grpc.ManagedChannelBuilder
            .forTarget("localhost:50051")
            .usePlaintext()
            .build()

        return PermissionsServiceGrpc
            .newBlockingStub(channel)
            .withCallCredentials(BearerToken("wolfdesk"))
    }
}
