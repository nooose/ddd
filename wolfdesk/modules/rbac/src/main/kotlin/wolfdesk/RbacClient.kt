package wolfdesk

/**
 * 권한 등록 및 검증을 수행하는 RBAC 클라이언트
 */
interface RbacClient {

    /**
     * 특정 대상이 지정된 리소스에 대해 특정 권한을 가지고 있는지 확인
     *
     * @param resource 권한을 확인할 리소스 객체
     * @param subject 권한을 확인할 대상 객체
     * @param permission 확인할 권한 유형 객체
     *
     * @return 대상이 해당 리소스에 대해 지정된 권한을 가지고 있는지 여부를 반환
     */
    fun hasPermission(
        resource: RbacResource,
        subject: RbacSubject,
        permission: Permission
    ): Boolean

    /**
     * 리소스에 접근 가능한 대상을 조회
     *
     * @param resource 리소스 객체
     * @param subjectType 대상 유형
     * @param permission 확인할 권한 유형
     *
     * @return 특정 리소스에 접근 가능한 대상 목록 반환
     */
    fun getAccessibleSubjects(
        resource: RbacResource,
        subjectType: SubjectType,
        permission: Permission,
    ): List<SubjectResponse>

    /**
     * 대상이 접근 가능한 리소스를 조회
     *
     * @param subject 대상 객체
     * @param resourceType 리소스 유형
     * @param permission 확인할 권한 유형
     *
     * @return 특정 리소스에 접근 가능한 대상 목록 반환
     */
    fun getAccessibleResources(
        subject: RbacSubject,
        resourceType: ResourceType,
        permission: Permission,
    ): List<ResourceResponse>

    /**
     * 리소스와 대상 간 관계를 설정
     *
     * @param resource 리소스 객체
     * @param relation 관계
     * @param subject 대상 객체
     */
    fun setRelationShip(
        resource: RbacResource,
        relation: RelationType,
        subject: RbacSubject,
    )

    /**
     * 관계 설정 벌크 요청
     *
     * @param requests 관계 요청 객체
     */
    fun setRelationShips(requests: List<RelationShipRequest>)
}
