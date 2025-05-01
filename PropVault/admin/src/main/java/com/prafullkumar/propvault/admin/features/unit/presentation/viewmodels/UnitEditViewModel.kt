package com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.DealStatus
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.unit.domain.UnitRepository
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class UnitEditUiState(
    val name: String = "",
    val unitCode: String = "",
    val unitType: String = "",
    val price: String = "",
    val status: String = "",
    val showDealSection: Boolean = false,
    val existingDeal: Boolean = false,
    val customerName: String = "",
    val customerPhone: String = "",
    val customerEmail: String = "",
    val dealTotalPrice: String = "",
    val assignedAgent: String = "",
    val dealStatus: DealStatus = DealStatus.PENDING,
    val dealRemarks: String = "",
    val dealDate: String = "",
    val closingDate: String = "",
    val dueAmount: String = "",
    val depositedAmount: String = "",
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val paymentDate: String = "",
    val paymentRemarks: String = "",
    val showValidationError: Boolean = false,
    val validationMessage: String = "",
    val totalPaymentPrice: String = "",
    val amount: String = "",
    val unitCreationDate: String = "",
)


class UnitEditViewModel(
    private val unitId: String
) : ViewModel(), KoinComponent {

    var isSaving by mutableStateOf(false)
    private val repository by inject<UnitRepository>()
    private val _unitWithDetails = MutableStateFlow<Resource<UnitWithDealsAndPayments>>(
        Resource.Loading
    )
    val unitWithDetails = _unitWithDetails.asStateFlow()

    var uiState by mutableStateOf(
        UnitEditUiState()
    )

    init {
        if (unitId != "") {
            getUnitDetails()
        } else {
            _unitWithDetails.update {
                Resource.Success(
                    UnitWithDealsAndPayments(
                        unit = PropertyUnit(
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            0.0,
                            "",
                            "",
                            emptyList()
                        ),
                        deals = emptyList()
                    )
                )
            }
        }
    }

    fun getUnitDetails() {
        viewModelScope.launch {
            if (unitId != "") {
                repository.getUnitDetails(unitId).collectLatest { response ->
                    _unitWithDetails.update {
                        response
                    }
                }
                if (_unitWithDetails.value is Resource.Success) {
                    val result =
                        (_unitWithDetails.value as Resource.Success<UnitWithDealsAndPayments>).data
                    val unit = result.unit
                    val deal = result.deals.firstOrNull()
                    val payment = deal?.payments?.firstOrNull()

                    uiState = uiState.copy(
                        name = unit.name,
                        unitCode = unit.unitCode ?: "",
                        unitType = unit.unitType ?: "",
                        price = unit.price.toString(),
                        status = unit.status ?: "",
                        showDealSection = deal != null,
                        existingDeal = deal != null,
                        customerName = deal?.deal?.customerName ?: "",
                        customerPhone = deal?.deal?.customerPhone ?: "",
                        customerEmail = deal?.deal?.customerEmail ?: "",
                        dealTotalPrice = deal?.deal?.totalPrice?.toString() ?: "",
                        assignedAgent = deal?.deal?.assignedAgent ?: "",
                        dealStatus = deal?.deal?.status ?: DealStatus.PENDING,
                        dealRemarks = deal?.deal?.remarks ?: "",
                        dueAmount = payment?.amount?.toString() ?: "",
                        depositedAmount = payment?.depositedAmount?.toString() ?: "",
                        paymentStatus = payment?.status ?: PaymentStatus.PENDING,
                        paymentRemarks = payment?.remarks ?: "",
                        dealDate = deal?.deal?.dealDate ?: "",
                        closingDate = deal?.deal?.closingDate ?: "",
                        paymentDate = payment?.paymentDate ?: "",
                        totalPaymentPrice = payment?.totalPrice?.toString() ?: "",
                    )
                } else {
                    uiState = uiState.copy(
                        showValidationError = true,
                        validationMessage = "No data found for the given unit ID."
                    )
                }
            }
        }
    }

    fun validateAndSave(
        isEditMode: Boolean,
        unitId: String,
        developmentId: String,
        developmentName: String,
        buildingId: String,
        buildingName: String,
        onSuccess: (Boolean) -> Unit
    ) {
        isSaving = true
        viewModelScope.launch {
            val data = (unitWithDetails.value as Resource.Success<UnitWithDealsAndPayments>).data
            try {
                val unit = PropertyUnit(
                    id = unitId,
                    name = uiState.name,
                    unitCode = uiState.unitCode,
                    unitType = uiState.unitType,
                    price = uiState.price.toDoubleOrNull() ?: 0.0,
                    status = uiState.status,
                    developmentId = developmentId,
                    developmentName = developmentName,
                    buildingId = buildingId,
                    buildingName = buildingName,
                    createdTime = uiState.unitCreationDate
                )
                var deal: Deal? = null
                var payment: Payment? = null
                if (uiState.showDealSection) {
                    deal = Deal(
                        id = if (isEditMode && data.deals.isNotEmpty()) data.deals[0].deal.id
                        else "",
                        customerName = uiState.customerName,
                        unitId = "",
                        customerPhone = uiState.customerPhone,
                        customerEmail = uiState.customerEmail,
                        totalPrice = uiState.dealTotalPrice.toDoubleOrNull() ?: 0.0,
                        assignedAgent = uiState.assignedAgent,
                        status = uiState.dealStatus,
                        remarks = uiState.dealRemarks,
                        dealDate = uiState.dealDate,
                        closingDate = uiState.closingDate.takeIf { it.isNotBlank() })
                    payment = Payment(
                        id = if (isEditMode && data.deals.isNotEmpty() && data.deals[0].payments.isNotEmpty()) data.deals[0].payments[0].id
                        else "",
                        dealId = "",
                        unitId = "",
                        amount = uiState.amount.toDoubleOrNull() ?: 0.0,
                        dueAmount = uiState.dueAmount.toDoubleOrNull() ?: 0.0,
                        depositedAmount = uiState.depositedAmount.toDoubleOrNull() ?: 0.0,
                        status = uiState.paymentStatus,
                        remarks = uiState.paymentRemarks,
                        paymentDate = uiState.paymentDate,
                        totalPrice = uiState.totalPaymentPrice.toDoubleOrNull() ?: 0.0
                    )
                }
                if (isEditMode) {
                    Log.d("UnitEditViewModel", "Updating unit: $unit")
                    Log.d("UnitEditViewModel", "Updating deal: $deal")
                    Log.d("UnitEditViewModel", "Updating payment: $payment")
                    repository.updateUnit(unit, deal, payment).collectLatest {
                        if (it is Resource.Success) {
                            onSuccess(it.data)
                            isSaving = false
                        } else {
                            onSuccess(false)
                        }
                    }
                } else {
                    Log.d("UnitEditViewModel", "Adding unit: $unit")
                    repository.addUnit(unit, deal, payment).collectLatest {
                        if (it is Resource.Success) {
                            onSuccess(it.data)
                        } else {
                            onSuccess(false)
                        }
                    }
                }
            } catch (e: Exception) {
                isSaving = false
                onSuccess(false)
            }

        }
    }
}
